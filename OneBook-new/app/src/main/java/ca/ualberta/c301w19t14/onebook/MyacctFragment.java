package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MyacctFragment extends Fragment {

    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_my_accnt_activity,container, false);



        return myView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView name = getView().findViewById(R.id.name);
        TextView phone = getView().findViewById(R.id.phone);
        TextView email = getView().findViewById(R.id.email);
        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Button UserEdit = getView().findViewById(R.id.UserInfoButton);
        UserEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),EditUserAcitivity.class));
            }
        });
        //name.setText("Hello, " + FirebaseAuth.getInstance().getCurrentUser().getEmail());

    }
}