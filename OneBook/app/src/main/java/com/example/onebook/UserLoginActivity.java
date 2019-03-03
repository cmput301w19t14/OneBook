package com.example.onebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserLoginActivity extends AppCompatActivity {

    public Button loginbutton;
    public EditText emailtext;
    public EditText passtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbutton = (Button) findViewById(R.id.UL_loginbutton);
        emailtext = (EditText) findViewById(R.id.UL_useredit);
        passtext = (EditText) findViewById(R.id.UL_passedit);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public boolean login(){

        String email, pass;

        email = emailtext.getText().toString();
        pass = passtext.getText().toString();

        //TODO: interact with firebase and login user or return false

        return true;
    }
}
