package com.example.onebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserRegisterActivity extends AppCompatActivity {

    private User newuser;
    public Button registerbutton;
    public EditText useredit;
    public EditText nameedit;
    public EditText passedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerbutton = (Button) findViewById(R.id.UR_loginbutton);
        useredit = (EditText) findViewById(R.id.UL_useredit);
        nameedit = (EditText) findViewById(R.id.UR_editname);
        passedit = (EditText) findViewById(R.id.UR_passedit);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
                //TODO: upload user to firebase
            }
        });
    }

    public boolean registerNewUser(){

        String email, name, pass;
        email = useredit.getText().toString();
        name = nameedit .getText().toString();
        pass = passedit.getText().toString();

        newuser = new Borrower(name, pass, email);

        return true;
    }
}
