package ca.ualberta.c301w19t14.onebook.fragements;

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

/**Fragment that diplays when messages is chosen in the navigation menu
 Still need to implement this in part 5
 @author CMPUT 301 Team 14*/
public class MessagesFragment extends Fragment {

    View myView;
    GeneralUtil util;
    Globals globals;

    ViewPageAdapter viewPageAdapter;

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