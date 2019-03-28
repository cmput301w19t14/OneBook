package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

//this class shows a user's information when someone clicks on the owner of a book
public class UserAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_my_accnt_activity);

        TextView nm = findViewById(R.id.Name);
        TextView em = findViewById(R.id.email);

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        final Book book = Globals.getInstance().books.getData().child(bundle.getString("id")).getValue(Book.class);

        String str_email = "Email: " + book.getOwner().getEmail();
        em.setText(str_email);

        String str_name = "Name: " + book.getOwner().getName();
        nm.setText(str_name);
    }
}

