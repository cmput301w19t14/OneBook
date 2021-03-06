package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import ca.ualberta.c301w19t14.onebook.fragments.BorrowingFragment;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.fragments.LendingFragment;
import ca.ualberta.c301w19t14.onebook.fragments.MessagesFragment;
import ca.ualberta.c301w19t14.onebook.fragments.MyProfileFragment;
import ca.ualberta.c301w19t14.onebook.fragments.NotificationFragment;
import ca.ualberta.c301w19t14.onebook.R;

/**
 * This class implements the main functionality of the app.
 * From this class, the navigation bar can be used and all of the fragments can be accessed.
 * @author CMPUT301 Team14: Dimitri T, Anastasia B, Natalie H.
 * @version 1.0
 * */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NotificationFragment notificationFragment;
    NavigationView navigationView;
    Toolbar toolbar = null;
    Globals globals;

    /**
     * Initialize the view
     * @param savedInstanceState: last instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize singleton Globals
        globals = Globals.getInstance();
        globals.initFirebaseUtil();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        notificationFragment = new NotificationFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, notificationFragment);
        fragmentTransaction.commit();
    }

    /**
     * Resume the view/acitvity with any changes
     */
    @Override
    protected void onResume() {
        super.onResume();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView name = (TextView) headerView.findViewById(R.id.nav_name);
        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        TextView email = (TextView) headerView.findViewById(R.id.nav_email);
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    //for Navigation menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //for Navigation menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    /**
     * Navigation drawer method. Proceeds to the right fragment/activity
     * @param item: item selected by user in the drawer
     * @return a boolean indicating if nothing was done
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        //open fragments depending on what was clicked in the nagivation menu
        if (id == R.id.nav_notifications) {
            NotificationFragment notificationFragment = new NotificationFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, notificationFragment);
            fragmentTransaction.addToBackStack(null).commit();
        }
        else if (id == R.id.nav_borrowing) {
                BorrowingFragment borrowingFragment = new BorrowingFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, borrowingFragment);
                fragmentTransaction.addToBackStack(null).commit();
        }

        else if (id == R.id.nav_lending) {
            LendingFragment lendingFragment = new LendingFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, lendingFragment);
            fragmentTransaction.addToBackStack(null).commit();
        }

        else if (id == R.id.nav_myacct) {
            MyProfileFragment myProfileFragment = new MyProfileFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, myProfileFragment);
            fragmentTransaction.addToBackStack(null).commit();
        }

        else if (id == R.id.nav_messages) {
            MessagesFragment messagesFragment = new MessagesFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, messagesFragment);
            fragmentTransaction.addToBackStack(null).commit();
        }

        else if (id == R.id.nav_logout) {
            startActivity( new Intent(MainActivity.this, UserLoginActivity.class));
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * For when the back button is pressed and dependent on the drawer state.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}