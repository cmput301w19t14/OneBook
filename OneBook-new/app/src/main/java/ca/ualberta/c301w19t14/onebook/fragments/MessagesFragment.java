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
import ca.ualberta.c301w19t14.onebook.util.GeneralUtil;

/**
 * This class implements the Messaging Functionality in different fragments for the app.
 * From this class, the fragments is linked to user and chat fragments.
 * These fragments are held by a ViewPager and have their own methods.
 * @author jandaile CMPUT 301 team 14
 * @since 2019-03-29
 * @version 1.0
 */
public class MessagesFragment extends Fragment {

    View myView;
    GeneralUtil util;
    Globals globals;

    ViewPageAdapter viewPageAdapter;

    /**
     * Initializes the view.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_messages_fragments, container, false);

        //get globals
        globals = Globals.getInstance();
        util = new GeneralUtil();

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