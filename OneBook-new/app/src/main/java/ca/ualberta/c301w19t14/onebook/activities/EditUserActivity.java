package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.User;

/**This class allows a user to edit their account information
 * @author CMPUT 301 Team 14*/
public class EditUserActivity extends AppCompatActivity {

    private EditText nm_edit;
    private EditText em_edit;
    private EditText ps_edit;
    private Button ph_edit;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_user_activity);

        // autofill current account information
        nm_edit = findViewById(R.id.editName);
        em_edit = findViewById(R.id.editEmail);
        ps_edit = findViewById(R.id.editPassword);
        ph_edit = findViewById(R.id.editPhoto);

        em_edit.setText(Globals.getInstance().user.getEmail());
        nm_edit.setText(Globals.getInstance().user.getDisplayName());


        ph_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery,100);
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
