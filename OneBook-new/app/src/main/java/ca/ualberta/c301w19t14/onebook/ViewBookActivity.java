package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * This class allows a user to view book information for a book that they own
 * Still need to add the book description
 * Still need to implement the set location so that a user can choose a location on the map
 * */

public class ViewBookActivity extends AppCompatActivity {
//need to add description update

    private TextView title;
    private TextView author;
    private TextView isbn;
    private TextView owner;
    private TextView description;
    private TextView status;
    private Book book;
    private String book_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_book_main);

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        book_id = bundle.getString("id");
        updateData(book_id);


        Button editButton =  findViewById(R.id.editBookButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewBookActivity.this, editBookActivity.class);
                intent.putExtras(bundle);
                ViewBookActivity.this.startActivity(intent);
            }
        });

        Button locationButton =  findViewById(R.id.GetLocationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(ViewBookActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
  
    @Override
    public void onResume(){
        super.onResume();

        if (Globals.getInstance().books.getData().child(book_id).getValue(Book.class) == null) {
            finish();
            Log.d("DEBUG_ONEBOOK", "book is equal to null");
        }

        else if(!book_id.isEmpty()) {
            updateData(book_id);
        }
    }

    private void updateData(String id) {
        if(id != null) {
            title = findViewById(R.id.bookTitle);
            author = findViewById(R.id.bookauthor);
            isbn = findViewById(R.id.bookIsbn);
            owner = findViewById(R.id.bookOwner);
            description = findViewById(R.id.bookDescription);
            status = findViewById(R.id.bookStatus);

            book = Globals.getInstance().books.getData().child(id).getValue(Book.class);

            String str_title = "Title: " + book.getTitle();
            title.setText(str_title);

            String str_author = "Author: " + book.getAuthor();
            author.setText(str_author);

            String str_ISBN = "ISBN: " + Long.toString(book.getIsbn());
            isbn.setText(str_ISBN);

            String str_owner = "Owner: " + book.getOwner().getName();
            owner.setText(str_owner);

            String str_description = "Description: " + book.getDescription();
            description.setText(str_description);

            String str_status = "Status: " + book.getStatus();
            status.setText(str_status);
        }
    }
}
