package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.util.DykUtil;

/**
 * This class shows a user's information when someone clicks on the owner of a book.
 *
 * @author CMPUT301 Team14
 * @version 1.0
 */
public class UserAccountActivity extends AppCompatActivity {
    private final String TAG = "UserAccountActivity";
    private ImageView profilePic;
    private boolean hasImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_user_profile);


        TextView nm = findViewById(R.id.Name);
        TextView em = findViewById(R.id.email);
        profilePic = findViewById(R.id.profilePicture);

        DykUtil dyk = new DykUtil();
        TextView dykText = findViewById(R.id.dyk);
        dykText.setText(dyk.getDyk());

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
                            hasImage = true;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hasImage = false;

            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasImage) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popup = inflater.inflate(R.layout.image_pop_up, null);
                    ImageView picture = popup.findViewById(R.id.ImageCloseUp);
                    picture.setImageBitmap(((BitmapDrawable) profilePic.getDrawable()).getBitmap());
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

    }

}

