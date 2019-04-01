package ca.ualberta.c301w19t14.onebook.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.activities.EditUserActivity;
import ca.ualberta.c301w19t14.onebook.util.DykUtil;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * This fragments displays a user profile page with all their information.
 * @author CMPUT301 Team14: Natalie H, Oran R.
 * @version 1.0
 */
public class MyProfileFragment extends Fragment {

    View myView;
    private TextView nm;
    private TextView em;

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

        nm = myView.findViewById(R.id.Name);
        em = myView.findViewById(R.id.email);

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

    @Override
    public void onResume()  {
        super.onResume();


        //https://stackoverflow.com/questions/17237287/how-can-i-wait-for-10-second-without
        // -locking-application-ui-in-android
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 2 seconds
                //Toast.makeText(getContext(),"Updating for new information", Toast.LENGTH_SHORT).show();
                String str_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                em.setText(str_email);

                String str_name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                nm.setText(str_name);
            }
        }, 3000);
        updateUserInfo();

    }

    public void updateUserInfo() {

        //Toast.makeText(this.getContext(),"Updating for new information", Toast.LENGTH_SHORT).show();


        String str_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        em.setText(str_email);

        String str_name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        nm.setText(str_name);

    }
}
