package ca.ualberta.c301w19t14.onebook.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * This class implements the ViewPageAdapter for the chat and user fragments.
 * From this class, the user and chat fragments will be attached to the MessagingFragment.
 * @author jandaile CMPUT 301 team 14
 * @see ca.ualberta.c301w19t14.onebook.fragments.MessagesFragment
 * @since 2019-03-29
 * @version 1.0
 */
public class ViewPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public ViewPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragments = new ArrayList<>();
        this.titles = new ArrayList<>();
    }

    /**
     * gets a fragment index
     * @param i: fragment index
     * @return user and chat fragments
     */
    @Override
    public Fragment getItem(int i) {

        return fragments.get(i);
    }

    /**
     * gets the total number of fragments in the ViewPager
     * @return number of fragments in ViewPager
     */
    @Override
    public int getCount() {

        return fragments.size();
    }

    /**
     * adds a fragment to the ViewPager
     * @param fragment: what fragment to add
     * @param title: name of the fragment
     */
    public void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        titles.add(title);
    }

    /**
     * gets the title character strings
     * @param i: index of string
     * @return page title character
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int i) {

        return titles.get(i);
    }
}
