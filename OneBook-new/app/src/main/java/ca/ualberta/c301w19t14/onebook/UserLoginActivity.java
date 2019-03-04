package com.example.onebook;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserLoginActivity extends AppCompatActivity {

    public Button loginbutton;
    public EditText emailtext;
    public EditText passtext;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DataSnapshot data;
    private String actual_password;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbutton = (Button) findViewById(R.id.UL_loginbutton);
        emailtext = (EditText) findViewById(R.id.UL_useredit);
        passtext = (EditText) findViewById(R.id.UL_passedit);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data = dataSnapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean worked = login();

                //DEBUG - test to see if it worked
                if (worked){
                    String str = "The user's phone number is: " +
                            Long.toString(user.getPhone());
                    Toast.makeText(getApplicationContext(), str,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean login(){

        String email, pass;
        boolean was_successful = false;

        email = emailtext.getText().toString();
        pass = passtext.getText().toString();

        email = removeAt(email);
        email = removeDot(email);

        //Now we process the login, processing the given email & pass against the
        //ones held in the database
        actual_password = (String) data.child("Users").child(email).child("Password").getValue();

        //first, check to see if the email given even matches with an email held
        //in the database
        if (TextUtils.isEmpty(actual_password)){
            Toast.makeText(getApplicationContext(), "Invalid email",
                    Toast.LENGTH_SHORT).show();
        }
        //so the email has correspondence. Check to see whether the given password matches
        //the one held in the database under that particular email
        else if (actual_password.equals(pass)){
            //DEBUG - remove later
            Toast.makeText(getApplicationContext(), "Correct!",
                    Toast.LENGTH_SHORT).show();

            //gather the rest of the data
            String name = (String) data.child("Users").child(email).child("Name").getValue();
            long phone = (long) data.child("Users").child(email).child("Phone").getValue();
            long userID = (long) data.child("Users").child(email).child("UserID").getValue();

            //put the special characters back in
            email = putAt(email);
            email = putDot(email);

            //load in the user
            user = new Borrower(name, pass, email, phone, userID);

            was_successful = true;
        }
        //otherwise, the password was incorrect and the user data is not loaded in
        else {
            Toast.makeText(getApplicationContext(), "Incorrect password",
                    Toast.LENGTH_SHORT).show();
        }

        return was_successful;
    }

    public static String removeAt(String email){
        char[] emailarray = email.toCharArray();
        for (int i = 0; i < emailarray.length; i++){
            if (emailarray[i] == '@')
                emailarray[i] = '*';
        }
        email = String.valueOf(emailarray);
        return email;
    }

    public static String putAt(String email){
        char[] emailarray = email.toCharArray();
        for (int i = 0; i < emailarray.length; i++){
            if (emailarray[i] == '*')
                emailarray[i] = '@';
        }
        email = String.valueOf(emailarray);
        return email;
    }

    public static String removeDot(String email){
        char[] emailarray = email.toCharArray();
        for (int i = 0; i < emailarray.length; i++){
            if (emailarray[i] == '.')
                emailarray[i] = '_';
        }
        email = String.valueOf(emailarray);
        return email;
    }

    public static String putDot(String email){
        char[] emailarray = email.toCharArray();
        for (int i = 0; i < emailarray.length; i++){
            if (emailarray[i] == '_')
                emailarray[i] = '.';
        }
        email = String.valueOf(emailarray);
        return email;
    }
}
