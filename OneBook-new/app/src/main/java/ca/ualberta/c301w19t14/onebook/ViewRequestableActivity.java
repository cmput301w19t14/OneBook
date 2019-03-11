package ca.ualberta.c301w19t14.onebook;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

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
        final String bookId = intent.getStringExtra("BOOK_ID");

        title = findViewById(R.id.bookTitle);
        author = findViewById(R.id.bookauthor);
        isbn = findViewById(R.id.bookIsbn);
        owner = findViewById(R.id.bookOwner);
        description = findViewById(R.id.bookDescription);
        status = findViewById(R.id.bookStatus);

        final Bundle bundle = intent.getExtras();
        title.setText(bundle.getString("TITLE"));
        author.setText(bundle.getString("AUTHOR"));
        isbn.setText(Long.toString(bundle.getLong("ISBN")));
        owner.setText(bundle.getString("NAME"));
        status.setText(bundle.getString("STATUS"));
        description.setText(bundle.getString("DESCRIPTION"));

        Button requestButton =  findViewById(R.id.requestBookButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add request action here
            }
        });

        Button locationButton =  findViewById(R.id.GetLocationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(ViewRequestableActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}