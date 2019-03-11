package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

public class editBookActivity extends AppCompatActivity {

    private EditText title;
    private EditText author;
    private EditText isbn;
    private TextView owner;
    private EditText description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_book_main);

        Intent intent = getIntent();

        title = findViewById(R.id.editBookTitle);
        author = findViewById(R.id.editBookAuthor);
        isbn = findViewById(R.id.editBookISBN);
        owner = findViewById(R.id.viewBookOwner);
        description = findViewById(R.id.editBookDescription);

        final Bundle bundle = intent.getExtras();
        title.setText(bundle.getString("TITLE"));
        author.setText(bundle.getString("AUTHOR"));
        isbn.setText(Long.toString(bundle.getLong("ISBN")));
        owner.setText(bundle.getString("NAME"));

        String bookId = intent.getStringExtra("FINAL_BOOK_ID");

        Button saveButton =  findViewById(R.id.saveBookButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(editBookActivity.this, ViewBookActivity.class);
                startActivity(intent);
            }
        });

        Button deleteButton =  findViewById(R.id.deleteBookButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(editBookActivity.this, LendingFragment.class);
                startActivity(intent);
            }
        });
    }
}
