package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

public class ViewBookActivity extends AppCompatActivity {
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
        setContentView(R.layout.view_book_main);

        Intent intent = getIntent();
        final String bookId = intent.getStringExtra("BOOK_ID");

        title = findViewById(R.id.bookTitle);
        author = findViewById(R.id.bookauthor);
        isbn = findViewById(R.id.bookIsbn);
        owner = findViewById(R.id.bookOwner);
        description = findViewById(R.id.bookDescription);
        status = findViewById(R.id.bookStatus);

        Bundle bundle = intent.getExtras();
        title.setText(bundle.getString("TITLE"));
        author.setText(bundle.getString("AUTHOR"));
        isbn.setText(Long.toString(bundle.getLong("ISBN")));
        owner.setText(bundle.getString("OWNER"));
        status.setText(bundle.getString("STATUS"));

        Button editButton =  findViewById(R.id.editBookButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(ViewBookActivity.this, editBookActivity.class);
                intent.putExtra( "EDIT_BOOK_ID", bookId);
                startActivity(intent);
            }
        });

        Button locationButton =  findViewById(R.id.locationBookButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(ViewBookActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
