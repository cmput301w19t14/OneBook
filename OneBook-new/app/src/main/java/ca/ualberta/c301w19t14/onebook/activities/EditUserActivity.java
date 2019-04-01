package ca.ualberta.c301w19t14.onebook.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

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
    private ImageView profilePicture;
    private final int REQUEST_IMAGE_CAPTURE = 100;
    private final int REQUEST_IMAGE_GALLERY = 101;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference profile_ref;

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
        profilePicture = findViewById(R.id.profilePicture);
        em_edit.setText(Globals.getInstance().user.getEmail());
        nm_edit.setText(Globals.getInstance().user.getDisplayName());
        profile_ref = storage.getReference("Profile pictures/"+FirebaseAuth.getInstance()
                .getUid()+"/profile.png");

        ph_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder fBuilder = new AlertDialog.Builder(EditUserActivity.this);


                fBuilder.setPositiveButton("From Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(gallery,REQUEST_IMAGE_GALLERY);
                    }
                });
                fBuilder.setNegativeButton("From Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                });

                fBuilder.setTitle("Select photo");

                AlertDialog fDialog = fBuilder.create();
                fDialog.show();
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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Bitmap imageBitmap = ((BitmapDrawable) profilePicture.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            byte[] byteArray = baos.toByteArray();
                            UploadTask uploadTask = profile_ref.putBytes(byteArray);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditUserActivity.this, "failed data commit", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                    Toast.makeText(EditUserActivity.this, "Data commited", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        catch(ClassCastException e)
                        {
                            profile_ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditUserActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditUserActivity.this, "Data is still there idiot", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                startActivity(new Intent(EditUserActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == REQUEST_IMAGE_CAPTURE)
            {
                Bundle extras = data.getExtras();
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
                profilePicture.setImageBitmap(imageBitmap);
                Toast.makeText(EditUserActivity.this, "Camera selection", Toast.LENGTH_SHORT).show();
            }
            if(requestCode == REQUEST_IMAGE_GALLERY)
            {
                Uri uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    // Log.d(TAG, String.valueOf(bitmap));
                    profilePicture.setImageBitmap(bitmap);
                }
                catch (Exception e)
                {
                    Toast.makeText(EditUserActivity.this, "Gallery failure", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
