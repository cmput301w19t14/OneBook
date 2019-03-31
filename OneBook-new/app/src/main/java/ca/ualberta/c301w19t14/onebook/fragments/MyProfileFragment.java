package ca.ualberta.c301w19t14.onebook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.activities.EditUserActivity;
import ca.ualberta.c301w19t14.onebook.models.User;

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