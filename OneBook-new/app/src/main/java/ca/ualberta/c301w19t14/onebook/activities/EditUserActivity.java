package ca.ualberta.c301w19t14.onebook.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

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

/**
 * This class allows a user to edit their account information.
 * @author CMPUT301 Team14: Natalie H, Oran R, Anastasia B
 * @version 1.0
 * @see ca.ualberta.c301w19t14.onebook.fragments.MyProfileFragment {@link #onOptionsItemSelected}
 */
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
    private Boolean hasImage;


    /**
     * Initializes the view
     * @param savedInstanceState: last possible state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
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
        
        profile_ref.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                if(bytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    profilePicture.setImageBitmap(bitmap);
                    hasImage = true;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(EditUserActivity.this, "no profile pic", Toast.LENGTH_SHORT).show();
            }
        });
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasImage) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popup = inflater.inflate(R.layout.image_pop_up, null);
                    ImageView picture = popup.findViewById(R.id.ImageCloseUp);
                    picture.setImageBitmap(((BitmapDrawable) profilePicture.getDrawable()).getBitmap());
                    int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                    int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                    final PopupWindow popupWindow = new PopupWindow(popup, width, height, true);
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                    popup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
            }
        });
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

                User.updateName(FirebaseAuth.getInstance().getUid(), nameInput);
                User.updateEmail(FirebaseAuth.getInstance().getUid(), emailInput);

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
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                }
                            });

                        }
                        catch(ClassCastException e)
                        {
                            profile_ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                        }
                    }
                });

                finish();
            }
        });
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                }

            }
        }
    }
}
