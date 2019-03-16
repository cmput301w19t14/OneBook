package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

/**
 * This class allows a user to view book information for a book that they do not own
 * Still need to add the book description
 * Still need to implement the view location so that a user can see the correct pick up location
 * Still need to add request process
 * @author CMPUT 301 Team 14
 * */
public class ViewRequestableActivity extends AppCompatActivity {
//need to add description update

    private TextView title;
    private TextView author;
    private TextView isbn;
    private TextView owner;
    private TextView description;
    private TextView status;

    public FirebaseUtil books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestable_book);

        Intent intent = getIntent();

        title = findViewById(R.id.bookTitle);
        author = findViewById(R.id.bookauthor);
        isbn = findViewById(R.id.bookIsbn);
        owner = findViewById(R.id.bookOwner);
        description = findViewById(R.id.bookDescription);
        status = findViewById(R.id.bookStatus);

        final Bundle bundle = intent.getExtras();
        final Book book = Globals.getInstance().books.getData().child(bundle.getString("id")).getValue(Book.class);

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

        status.setText(book.getStatus());

        final User user = Globals.getInstance().users.getData().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);

        Button requestButton =  findViewById(R.id.requestBookButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request.requestBook(user, book);
            }
        });

    }
}