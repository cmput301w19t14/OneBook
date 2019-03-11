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

import com.google.firebase.auth.FirebaseAuth;
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
    public FirebaseUtil books;
    private Long ISBN_value;
    private String Owner_value;
    private int primary_key;
    private String TAG = "edit book activity";
    private String login_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_book_main);


        Intent intent = getIntent();
        this.books = new FirebaseUtil("Books");

        title = findViewById(R.id.editBookTitle);
        author = findViewById(R.id.editBookAuthor);
        isbn = findViewById(R.id.editBookISBN);
        owner = findViewById(R.id.viewBookOwner);
        description = findViewById(R.id.editBookDescription);

        final Bundle bundle = intent.getExtras();
        ISBN_value = bundle.getLong("ISBN");
        Owner_value = bundle.getString("NAME");
        title.setText(bundle.getString("TITLE"));
        author.setText(bundle.getString("AUTHOR"));
        isbn.setText(Long.toString(ISBN_value));
        owner.setText(Owner_value);

        String bookId = intent.getStringExtra("FINAL_BOOK_ID");

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //login_user = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();


        Button saveButton =  findViewById(R.id.saveBookButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(books != null) {
                    int i = 0;
                    for (DataSnapshot snapshot : books.getData().getChildren()) {
                        i++;
                        Book new_book = snapshot.getValue(Book.class);
                        //Log.d(TAG, "onClick: newbook owner vs owner value :"+new_book.getOwner().getName()+" "+Owner_value);
                        //Log.d(TAG, "onClick: newbook isbn vs isbn value :"+new_book.getIsbn()+" "+ISBN_value);

                        if(new_book.getOwner().getName().equals(Owner_value) && new_book.getIsbn() == ISBN_value)
                        {
                            primary_key = i;
                            Log.d("primary key test", "onClick: primary key is: "+primary_key);
                        }
                    }

                    //save data in database
                    String new_title = title.getText().toString();
                    String new_author = author.getText().toString();
                    String new_isbn = isbn.getText().toString();
                    String new_description = description.getText().toString();
                    //commit statements for the database, sets new values for title,description,isbn,author
                    mDatabase.child("Books").child(Integer.toString(primary_key)).
                            child("title").setValue(new_title);
                    mDatabase.child("Books").child(Integer.toString(primary_key)).
                            child("description").setValue(new_description);
                    mDatabase.child("Books").child(Integer.toString(primary_key)).
                            child("isbn").setValue(new_title);
                    mDatabase.child("Books").child(Integer.toString(primary_key)).
                            child("author").setValue(new_title);
                    //DatabaseReference booksRef = mDatabase.child("books");
                    //booksRef.setValue();

                    //return to book info page
                    Intent intent = new Intent(editBookActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button deleteButton =  findViewById(R.id.deleteBookButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete data from database


                //return to lending page
                Intent intent  = new Intent(editBookActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
