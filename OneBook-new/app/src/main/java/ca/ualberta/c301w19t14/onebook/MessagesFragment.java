package ca.ualberta.c301w19t14.onebook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**Fragment that diplays when messages is chosen in the navigation menu
 Still need to implement this in part 5
 @author CMPUT 301 Team 14*/
public class MessagesFragment extends Fragment {

    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_messages,container, false);


        return myView;
    }
}