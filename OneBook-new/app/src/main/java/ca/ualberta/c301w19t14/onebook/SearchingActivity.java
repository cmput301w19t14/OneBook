package ca.ualberta.c301w19t14.onebook;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchingActivity extends AppCompatActivity {

    public RecyclerView Recycler;
    public BookAdapter BA;
    public ArrayList<Book> books;
    public static String TAG = "Searching activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_main);

        Button SearchButton = findViewById(R.id.SearchButton);
        Recycler = findViewById(R.id.SearchRecycler);
        //BA = new BookAdapter(books);
        Recycler.setAdapter(BA);


        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.search);
                Log.d(TAG, "onClick: "+editText.getText().toString() );
                Set_recycleView(editText.getText().toString());
            }
        });

    }
    public void Set_recycleView(final String keyword)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Books");
        Query query = myRef.child("Books").orderByChild("title").equalTo(keyword);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot issue : dataSnapshot.getChildren())
                    {
                        Log.d(TAG, "onDataChange: "+ keyword);
                        Log.d(TAG, "onDataChange: "+ issue.getValue(Book.class).toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



}
