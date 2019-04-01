package ca.ualberta.c301w19t14.onebook.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.adapters.ViewPageAdapter;

/**
 * This class implements the Messaging Functionality in different fragments for the app.
 * From this class, the fragments is linked to user and chat fragments.
 * These fragments are held by a ViewPager and have their own methods.
 * @version 1.0
 * @author jandaile CMPUT 301 team 14
 * @since 2019-03-29
 */
public class MessagesFragment extends Fragment {

    View myView;
    Globals globals;

    ViewPageAdapter viewPageAdapter;

    /**
     * Initializes the view.
     * @param inflater: view inflater for the layout used
     * @param container: container to hold any views going into the View
     * @param savedInstanceState: last possible state
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_messages_fragments, container, false);

        //get globals
        globals = Globals.getInstance();

        TabLayout tabLayout = myView.findViewById(R.id.tabLayout);
        ViewPager viewPager = myView.findViewById(R.id.viewPager);

        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager());

        viewPageAdapter.addFragment(new MessagingChatsFragment(), "Chats");
        viewPageAdapter.addFragment(new MessagingUsersFragment(), "Users");

        viewPager.setAdapter(viewPageAdapter);

        tabLayout.setupWithViewPager(viewPager);

        return myView;
    }
}