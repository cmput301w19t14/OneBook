package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_book_main);

        Intent intent = getIntent();
        final String bookId = intent.getStringExtra("BOOK_ID");

        Button editButton =  findViewById(R.id.editBookButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(ViewBookActivity.this, editBookActivity.class);
                intent.putExtra( "EDIT_BOOK_ID", bookId);
                startActivity(intent);
            }
        });
    }
}
