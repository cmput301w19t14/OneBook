package ca.ualberta.c301w19t14.onebook.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.activities.EditUserActivity;
import ca.ualberta.c301w19t14.onebook.activities.UserAccountActivity;
import ca.ualberta.c301w19t14.onebook.models.User;
import ca.ualberta.c301w19t14.onebook.util.DykUtil;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;

/**
 * This fragments displays a user profile page with all their information.
 * @author CMPUT301 Team14: Natalie H
 * @version 1.0
 */
public class MyProfileFragment extends Fragment {

    View myView;

    /**
     * Initializes the view.
     *
     * @see this.loadData()
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View layout view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.content_my_accnt_activity, container, false);
        setHasOptionsMenu(true);

        TextView nm = myView.findViewById(R.id.Name);
        TextView em = myView.findViewById(R.id.email);

        String str_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        em.setText(str_email);

        String str_name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        nm.setText(str_name);

        DykUtil dyk = new DykUtil();
        TextView dykText = myView.findViewById(R.id.dyk);
        dykText.setText(dyk.getDyk());

        final ImageView profilePic = myView.findViewById(R.id.profilePicture);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
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
                catch(Exception e)
                {

                }

            }
        });

        FirebaseStorage.getInstance().getReference().child("Profile pictures/" +
                FirebaseAuth.getInstance().getUid() + "/profile.png").getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        if (bytes != null) {
                            Toast.makeText(myView.getContext(), "Data found", Toast.LENGTH_SHORT).show();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            profilePic.setImageBitmap(bitmap);
                            //hasImage = true;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //hasImage = false;
                Toast.makeText(myView.getContext(), "image not found", Toast.LENGTH_SHORT).show();

            }
        });

        return myView;
    }

    /**
     * Creates the options menu (top right).
     *
     * @param menu options menu
     * @param inflater MenuInflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pencil_toolbar, menu);
    }

    /**
     * Handles selecting an options menu item.
     *
     * @param item android id of the item clicked
     * @return boolean if the item was handled or not
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this.getContext(), EditUserActivity.class));
        return true;
    }

}
