package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

/**This class allows a user to edit their account information
 * @author CMPUT 301 Team 14*/
public class editBookActivity extends AppCompatActivity {

    private EditText title;
    private EditText author;
    private EditText isbn;
    private TextView owner;
    private EditText description;
    public FirebaseUtil books;

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
        final Book book = Globals.getInstance().books.getData().child(bundle.getString("id")).getValue(Book.class);
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(Long.toString(book.getIsbn()));
        owner.setText(book.getOwner().getName());
        description.setText(book.getDescription());

        Button saveButton =  findViewById(R.id.saveBookButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setAuthor(author.getText().toString());
                book.setTitle(title.getText().toString());
                book.setDescription(description.getText().toString());
                book.setIsbn(Long.valueOf(isbn.getText().toString()));

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Books");
                myRef.child(book.getId()).setValue(book);
                finish();
                }
            });

        Button deleteButton =  findViewById(R.id.deleteBookButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Books");
                myRef.child(book.getId()).removeValue();

                // TODO: MUST REMOVE REQUESTS
                finish();
            }
        });
    }
}
