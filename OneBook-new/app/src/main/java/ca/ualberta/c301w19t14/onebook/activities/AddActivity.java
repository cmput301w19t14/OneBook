package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.User;

/**
 * Add a book.
 *
 * @author Dimitri Trofimuk
 */
public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add);

        Intent intent = getIntent();

        if(intent.hasExtra("ISBN")) {
            ((EditText) findViewById(R.id.isbn)).setText(intent.getStringExtra("ISBN"));
        }

        final User user = Globals.getInstance().users.getData().child(Globals.getInstance().user.getUid()).getValue(User.class);
        Button btn = findViewById(R.id.add);

        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("Books");
                        String isbn = stringFromEditText(findViewById(R.id.isbn));
                        String title = stringFromEditText(findViewById(R.id.title));
                        String author = stringFromEditText(findViewById(R.id.author));
                        String desc = stringFromEditText(findViewById(R.id.description));

                        Book book = new Book(Long.parseLong(isbn), title, author, desc, user);
                        book.setId(ref.push().getKey());

                        ref.child(book.getId()).setValue(book);
                        finish();
                    }
                });
    }

    private String stringFromEditText(View e) {
        return ((EditText) e).getText().toString();
    }
}
