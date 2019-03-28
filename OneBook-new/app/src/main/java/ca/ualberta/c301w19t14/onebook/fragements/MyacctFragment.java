package ca.ualberta.c301w19t14.onebook.fragements;

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

/**This class diplays the current user's account information when they choose my account in the menu
 * @author CMPUT 301 Team 14*/
public class MyacctFragment extends Fragment{

    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.content_my_accnt_activity,container, false);

        return myView;
    }


   @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        TextView nm = getView().findViewById(R.id.Name);
        TextView em = getView().findViewById(R.id.email);

        String str_email = "Email: " + FirebaseAuth.getInstance().getCurrentUser().getEmail();
        em.setText(str_email);

        String str_name = "Name: " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        nm.setText(str_name);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pencil_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this.getContext(), EditUserActivity.class));
        return true;
    }

}