package ca.ualberta.c301w19t14.onebook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add);
        final User user = Globals.getInstance().users.getData().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);
        Button btn = findViewById(R.id.add);

        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Books");
                        String name = myRef.push().getKey();
                        EditText title = (EditText) findViewById(R.id.title);
                        EditText author = (EditText) findViewById(R.id.author);
                        EditText isbn = (EditText) findViewById(R.id.isbn);
                        EditText desc = (EditText) findViewById(R.id.description);

                        Book book = new Book(Long.parseLong(isbn.getText().toString()), title.getText().toString(), author.getText().toString(), desc.getText().toString(), user);
                        book.setId(name);
                        myRef.child(name).setValue(book);
                        finish();
                    }
                });
    }

}
