package ca.ualberta.c301w19t14.onebook;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

public class SearchingActivity extends AppCompatActivity {

    public RecyclerView Recycler;
    public BookAdapter BA;

    public static String TAG = "Searching activity";
    public FirebaseUtil books;
    public ArrayList<Book> rec_books = new ArrayList<Book>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_main);

        Button SearchButton = findViewById(R.id.SearchButton);
        Recycler = findViewById(R.id.SearchRecycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        Log.d(TAG, "onCreate: got through setorientation");
        Recycler.setLayoutManager(llm);
        Log.d(TAG, "onCreate: got through setlayout");

        BA = new BookAdapter(this,rec_books);
        Recycler.setAdapter(BA);

        /*

        */

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.search);
                //Log.d(TAG, "onClick: "+editText.getText().toString() );
                Set_recycleView(editText.getText().toString());
            }
        });
        this.books = new FirebaseUtil("Books");

    }
    public void Set_recycleView(final String keyword)
    {
        rec_books.removeAll(rec_books);
        if(this.books != null) {
            for (DataSnapshot snapshot : this.books.getData().getChildren()) {
                //Log.d(TAG, "Set_recycleView: "+snapshot.getValue(Book.class).toString());
                Book new_book;
                new_book = snapshot.getValue(Book.class);
                Log.d(TAG, "Set_recycleView: Book.getTitle is " + new_book.getTitle());
                if(new_book.getTitle().contains(keyword)) {
                    if (new_book != null) {
                        rec_books.add(new_book);
                    }
                }
            }
            BA.notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(this,"DataBase is loading",Toast.LENGTH_SHORT).show();
        }
    }



}
