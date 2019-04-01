package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ca.ualberta.c301w19t14.onebook.R;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView picture;
    private TextView name;
    private TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = findViewById(R.id.email);
        name = findViewById(R.id.Name);
        picture = findViewById(R.id.profilePicture);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();



    }

}
