package ca.ualberta.c301w19t14.onebook.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.adapters.RequestsAdapter;
import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.models.Request;

public class ViewRequestsActivity extends AppCompatActivity {

    private ArrayList<Request> requests = new ArrayList<>();
    private Book book;
    private RequestsAdapter adapter;


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
     * Loads borrowed books from database.
     * Manipulates the views during loading.
     */
    private void loadData() {
        final ProgressBar loader = findViewById(R.id.loader);
        findViewById(R.id.noData).setVisibility(View.GONE);
        findViewById(R.id.requestList).setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);

        if(book.getRequest() != null) {
            requests.addAll(book.getRequest().values());
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
}
