package ca.ualberta.c301w19t14.onebook;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;
import ca.ualberta.c301w19t14.onebook.util.GeneralUtil;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    Toolbar toolbar = null;
    Globals globals;
    GeneralUtil util;

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

            NotificationFragment notificationFragment = new NotificationFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, notificationFragment);
            fragmentTransaction.commit();
        }


        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
           //return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        util = new GeneralUtil();

        //process requests, if any
        //ArrayList<Request> requests = util.findOwnerRequests();

        //DEBUG
        //for (Request r : requests) {
        //    String str = String.valueOf(r.getISBN());
        //    Log.d("Requests", str);
       // }

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
}