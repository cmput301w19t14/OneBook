package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LendingActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lending_main);

       FloatingActionButton newBookButton =  findViewById(R.id.newBook);
        newBookButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                //add link to view book page here
                //Intent intent  = new Intent(LendingActivity.this, );
                //startActivity(intent);
            }
        });
    }
}
