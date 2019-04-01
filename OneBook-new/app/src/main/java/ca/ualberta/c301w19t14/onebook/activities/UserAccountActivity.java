package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import ca.ualberta.c301w19t14.onebook.R;

/**
 * This class shows a user's information when someone clicks on the owner of a book.
 *
 * @author CMPUT301 Team14: Oran R
 * @version 1.0
 */
public class UserAccountActivity extends AppCompatActivity {
    private final String TAG = "UserAccountActivity";
    private ImageView profilePic;

    /**
     * Initializes the view.
     * @param savedInstanceState: last possible state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_user_profile);


        TextView nm = findViewById(R.id.Name);
        TextView em = findViewById(R.id.email);
        profilePic = findViewById(R.id.profilePicture);

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();


        String str_email = "Email: " + bundle.getString("EMAIL");
        String str_name = "Name: " + bundle.getString("NAME");
        nm.setText(str_name);
        em.setText(str_email);

        //Log.d(TAG, "onCreate: "+bundle.getString("ID"));
        FirebaseStorage.getInstance().getReference().child("Profile pictures/" +
                bundle.getString("ID") + "/profile.png").getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        if (bytes != null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            profilePic.setImageBitmap(bitmap);
                            //hasImage = true;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //hasImage = false;

            }
        });

    }

}

