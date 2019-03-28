package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.Request;
import ca.ualberta.c301w19t14.onebook.models.User;
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
    public Book book;
    private String book_id;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestable_book);

        Intent intent = getIntent();



        final Bundle bundle = intent.getExtras();
        book_id = bundle.getString("id");

        //DEBUG - remove later
        //Toast toast = Toast.makeText(this, "Book ID: " + book_id, Toast.LENGTH_SHORT);
        //toast.show();

        updateData(book_id);


        final User user = Globals.getInstance().users.getData().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);

        Button requestButton =  findViewById(R.id.requestBookButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request.requestBook(user, book, book_id);
            }
        });

        //let's the user click on an owner to see their profile
        TextView owner = (TextView)findViewById(R.id.bookOwner2);
        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when the user clicks on their own name as owner
                Intent intent = new Intent(ViewRequestableActivity.this, UserAccountActivity.class);
                intent.putExtras(bundle);
                ViewRequestableActivity.this.startActivity(intent);
            }
        });


    }


    private void updateData(String id) {
        if(id != null) {
            title = findViewById(R.id.bookTitle2);
            author = findViewById(R.id.bookauthor2);
            isbn = findViewById(R.id.bookIsbn2);
            owner = findViewById(R.id.bookOwner2);
            description = findViewById(R.id.bookDescription2);
            status = findViewById(R.id.bookStatus2);

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