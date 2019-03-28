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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.fragements.BorrowingFragment;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.fragements.LendingFragment;
import ca.ualberta.c301w19t14.onebook.fragements.MessagesFragment;
import ca.ualberta.c301w19t14.onebook.fragements.MyacctFragment;
import ca.ualberta.c301w19t14.onebook.fragements.NotificationFragment;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.util.GeneralUtil;

/**This class implements the main functionality of the app
 * From this class, the navigation bar can be used and all of the fragments can be accessed.
 * @author CMPUT 301 Team 14*/
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NotificationFragment notificationFragment;
    NavigationView navigationView;
    Toolbar toolbar = null;
    Globals globals;
    GeneralUtil util;

        /**
        *
        * @param savedInstanceState
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

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            View headerView = navigationView.getHeaderView(0);
            TextView name = (TextView) headerView.findViewById(R.id.nav_name);
            name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            TextView email = (TextView) headerView.findViewById(R.id.nav_email);
            email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

            notificationFragment = new NotificationFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, notificationFragment);
            fragmentTransaction.commit();
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        util = new GeneralUtil();

        //open fragments depending on what was clicked in the nagivation menu
        if (id == R.id.nav_notifications) {
            NotificationFragment notificationFragment = new NotificationFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, notificationFragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_borrowing) {
            if (globals.books.isNull()){
                Toast.makeText(this, "Still loading data, please wait", Toast.LENGTH_SHORT).show();
            }
            else {

                BorrowingFragment borrowingFragment = new BorrowingFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, borrowingFragment);
                fragmentTransaction.commit();
            }
        }

        else if (id == R.id.nav_lending) {
            ArrayList<Book> book = new ArrayList<Book>();

            for(DataSnapshot snapshot : globals.books.getData().getChildren()) {
                Book b = snapshot.getValue(Book.class);

                if(b.getOwner().getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                {
                    book.add(b);
                }
            }

            LendingFragment lendingFragment = new LendingFragment(book);
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, lendingFragment);
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_myacct) {
            MyacctFragment myacctFragment = new MyacctFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, myacctFragment);
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_messages) {
            MessagesFragment messagesFragment = new MessagesFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, messagesFragment);
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_logout) {
            startActivity( new Intent(MainActivity.this, UserLoginActivity.class));
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // https://stackoverflow.com/questions/5448653/how-to-implement-onbackpressed-in-fragments
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}