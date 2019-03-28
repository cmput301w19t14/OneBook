package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;


/**
 * Edit book activity.
 *
 * @author Oran, Dimitri
 */
public class EditBookActivity extends AppCompatActivity {

    private EditText title;
    private EditText author;
    private EditText isbn;
    private EditText description;
    public FirebaseUtil books;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_book_main);

        Intent intent = getIntent();
        book = Book.find(intent.getStringExtra("id"));

        // init the views
        title = findViewById(R.id.editBookTitle);
        author = findViewById(R.id.editBookAuthor);
        isbn = findViewById(R.id.editBookISBN);
        TextView owner = findViewById(R.id.viewBookOwner);
        description = findViewById(R.id.editBookDescription);

        // set the current data
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(Long.toString(book.getIsbn()));
        owner.setText(book.getOwner().getName());
        description.setText(book.getDescription());

        // handle saving
        Button saveButton = findViewById(R.id.saveBookButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setAuthor(author.getText().toString());
                book.setTitle(title.getText().toString());
                book.setDescription(description.getText().toString());
                book.setIsbn(Long.valueOf(isbn.getText().toString()));
                book.update();

                finish();
                }
            });

        // handle deleting
        Button deleteButton = findViewById(R.id.deleteBookButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.delete();

                finish();
            }
        });
    }
}
