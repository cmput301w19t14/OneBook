package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.Request;
import ca.ualberta.c301w19t14.onebook.models.User;

/**
 * This class allows a user to view book information for a book that they do not own
 * Still need to add the book description
 * Still need to implement the view location so that a user can see the correct pick up location
 * Still need to add request process
 * @author CMPUT 301 Team 14
 * */
public class ViewBookActivity extends AppCompatActivity {

    public Book book;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestable_book);
        updateData(getIntent().getStringExtra("id"));

        final User user = Globals.getInstance().users.getData().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);

        Button requestButton = findViewById(R.id.request);
        if(!book.isOwner()) {
            requestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Request.requestBook(user, book, book.getId());
                }
            });
        } else {
            requestButton.setVisibility(View.GONE);
        }

        Button locBtn = findViewById(R.id.location);
        if(book.getAcceptedRequest() != null && (book.isOwner() || book.getAcceptedRequest().getUser().getUid().equals(Globals.getInstance().user.getUid()))) {
            locBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ViewBookActivity.this, MapsActivity.class);
                    intent.putExtra("book_id", book.getId());
                    startActivity(intent);
                }
            });
        } else {
            locBtn.setVisibility(View.GONE);
        }

        //let's the user click on an owner to see their profile
        TextView owner = findViewById(R.id.owner);
        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewBookActivity.this, UserAccountActivity.class);
                intent.putExtra("id", book.getOwner().getUid());
                ViewBookActivity.this.startActivity(intent);
            }
        });

    }


    private void updateData(String id) {
        if(id != null) {
            TextView title = findViewById(R.id.bookTitle2);
            TextView author = findViewById(R.id.bookauthor2);
            TextView isbn = findViewById(R.id.bookIsbn2);
            TextView owner = findViewById(R.id.owner);
            TextView description = findViewById(R.id.bookDescription2);
            TextView status = findViewById(R.id.bookStatus2);

            book = Globals.getInstance().books.getData().child(id).getValue(Book.class);

            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            isbn.setText(Long.toString(book.getIsbn()));
            owner.setText(book.getOwner().getName());
            description.setText(book.getDescription());
            status.setText(book.getStatus());
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(book != null) {
            return book.isOwner();
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.editIcon:
                if(book.isOwner()) {
                    Intent edit = new Intent(this, EditBookActivity.class);
                    edit.putExtra("id", book.getId());
                    startActivity(edit);
                }
                return true;
            case R.id.deleteIcon:
                if(book.isOwner()) {
                    book.delete();
                    finish();
                }
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}