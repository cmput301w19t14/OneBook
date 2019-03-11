package ca.ualberta.c301w19t14.onebook;

import android.content.ClipData;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

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

        em.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        nm.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
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