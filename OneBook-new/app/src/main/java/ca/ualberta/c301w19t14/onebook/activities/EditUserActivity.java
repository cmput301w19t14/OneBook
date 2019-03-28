package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.User;

/**This class allows a user to edit their account information
 * @author CMPUT 301 Team 14*/
public class EditUserActivity extends AppCompatActivity {

    private EditText nm_edit;
    private EditText em_edit;
    private EditText ps_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_user_activity);

        //autofill current account information
        nm_edit = findViewById(R.id.editName);
        em_edit = findViewById(R.id.editEmail);
        ps_edit = findViewById(R.id.editPassword);

        em_edit.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        nm_edit.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        //save changes to database
        Button saveEdit = findViewById(R.id.UserSaveButton);
        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailInput = em_edit.getText().toString();
                String nameInput = nm_edit.getText().toString();
                String passwordInput = ps_edit.getText().toString();

                User.updateEmail(FirebaseAuth.getInstance().getUid(), emailInput);
                User.updateName(FirebaseAuth.getInstance().getUid(), nameInput);
                if(!passwordInput.isEmpty()) {
                    User.updatePassword(FirebaseAuth.getInstance().getUid(), passwordInput);
                }
                startActivity(new Intent(EditUserActivity.this, MainActivity.class));
            }
        });
    }
}
