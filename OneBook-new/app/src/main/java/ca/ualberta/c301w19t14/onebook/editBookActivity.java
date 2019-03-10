package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

public class editBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_book_main);

        Button saveButton =  findViewById(R.id.saveBookButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(editBookActivity.this, ViewBookActivity.class);
                startActivity(intent);
            }
        });

        Button deleteButton =  findViewById(R.id.deleteBookButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent  = new Intent(editBookActivity.this, ?.class);  // new to change Lending to My books
                //startActivity(intent);
            }
        });
    }
}
