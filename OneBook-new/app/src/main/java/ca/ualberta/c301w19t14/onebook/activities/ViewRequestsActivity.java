package ca.ualberta.c301w19t14.onebook.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.adapters.RequestsAdapter;
import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.models.Request;

/**
 * This class shows all the requests on a book.
 *
 * @author CMPUT301 Team14: Dimitri T
 * @version 1.0
 *
 */
public class ViewRequestsActivity extends AppCompatActivity {

    private ArrayList<Request> requests = new ArrayList<>();
    private Book book;
    private RequestsAdapter adapter;


    /**
     * Initializes the view, adapter and recyclerView.
     * @see this.loadData()
     * @param savedInstanceState: instance state last used.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_requests);
        book = Book.find(getIntent().getStringExtra("id"));
        RecyclerView recyclerView = findViewById(R.id.requestList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        adapter = new RequestsAdapter(this, requests);
        recyclerView.setAdapter(adapter);

        loadData();
    }

    /**
     * This method loads borrowed books from database.
     * Manipulates the views during loading.
     */
    private void loadData() {
        final ProgressBar loader = findViewById(R.id.loader);
        findViewById(R.id.noData).setVisibility(View.GONE);
        findViewById(R.id.requestList).setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Books").child(book.getId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requests.clear();
                if(dataSnapshot.child("request").exists()) {
                    for (DataSnapshot ds : dataSnapshot.child("request").getChildren()) {
                        requests.add(ds.getValue(Request.class));
                    }
                }


                if(requests.isEmpty()) {
                    findViewById(R.id.noData).setVisibility(View.VISIBLE);
                    findViewById(R.id.requestList).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.noData).setVisibility(View.GONE);
                    findViewById(R.id.requestList).setVisibility(View.VISIBLE);
                }

                loader.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
