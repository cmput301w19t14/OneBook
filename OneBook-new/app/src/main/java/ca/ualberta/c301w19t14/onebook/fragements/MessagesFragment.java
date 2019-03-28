package ca.ualberta.c301w19t14.onebook.fragements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.util.GeneralUtil;

/**Fragment that diplays when messages is chosen in the navigation menu
 Still need to implement this in part 5
 @author CMPUT 301 Team 14*/
public class MessagesFragment extends Fragment {

    View myView;
    GeneralUtil util;
    Globals globals;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_messages_fragments,container, false);

        //get globals
        globals = Globals.getInstance();
        util = new GeneralUtil();

        TabLayout tabLayout = myView.findViewById(R.id.tabLayout);
        ViewPager viewPager = myView.findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());

        viewPagerAdapter.addFragment(new MessagingChatsFragment(), "Chats");
        viewPagerAdapter.addFragment(new MessagingUsersFragment(), "Users");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        return myView;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        // Ctrl + O

        @Nullable
        @Override
        public CharSequence getPageTitle(int i) {
            return titles.get(i);
        }
    }
}