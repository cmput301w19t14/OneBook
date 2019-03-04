package ca.ualberta.c301w19t14.onebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegisterActivity extends AppCompatActivity {

    private User newuser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public Button registerbutton;
    public EditText useredit;
    public EditText nameedit;
    public EditText passedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerbutton = (Button) findViewById(R.id.UR_loginbutton);
        useredit = (EditText) findViewById(R.id.UR_useredit);
        nameedit = (EditText) findViewById(R.id.UR_editname);
        passedit = (EditText) findViewById(R.id.UR_passedit);

        //assign firebase objects
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

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

        //replace illegal characters
        email = removeAt(email);
        email = removeDot(email);

        //add user to database
        myRef.child("Users").child(email).child("Password").setValue(pass);
        myRef.child("Users").child(email).child("Email").setValue(email);
        myRef.child("Users").child(email).child("Name").setValue(name);
        //TEMP: dummy variables for phone and userID, resolve this later
        myRef.child("Users").child(email).child("Phone").setValue(1000000000L);
        myRef.child("Users").child(email).child("UserID").setValue(99999L);

        newuser = new Borrower(name, pass, email);

        return true;
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
