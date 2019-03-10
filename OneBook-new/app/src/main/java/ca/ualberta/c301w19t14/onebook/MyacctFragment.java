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
        myView = inflater.inflate(R.layout.content_my_accnt_activity,container, false);

        return myView;
    }


   @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView nm = getView().findViewById(R.id.Name);
        TextView em = getView().findViewById(R.id.email);
        Button UserEdit = getView().findViewById(R.id.UserInfoButton);
        UserEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditUserActivity.class));
            }
        });

       em.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
       nm.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
    }
}