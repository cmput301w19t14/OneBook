package ca.ualberta.c301w19t14.onebook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.activities.ScanIsbnActivity;
import ca.ualberta.c301w19t14.onebook.models.Notification;
import ca.ualberta.c301w19t14.onebook.adapters.NotificationsAdapter;
import ca.ualberta.c301w19t14.onebook.R;

/**
 * Handles displaying notifications, and showing action buttons.
 *
 * @author Dimitri, Ana
 */
public class NotificationFragment extends Fragment {
    View v;
    ArrayList<Notification> notifications = new ArrayList<>();

    private NotificationsAdapter ba;

    /**
     * Initializes the view.
     *
     * @see this.loadData()
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View layout view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_home, container, false);
        setHasOptionsMenu(true);

        RecyclerView mRecyclerView = v.findViewById(R.id.requestList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ba = new NotificationsAdapter(getActivity(), notifications);
        mRecyclerView.setAdapter(ba);

        loadData();

        return v;
    }

    /**
     * Creates the options menu (top right).
     *
     * @param menu options menu
     * @param inflater MenuInflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.camera_toolbar, menu);
    }

    /**
     * Handles selecting an options menu item.
     *
     * @param item android id of the item clicked
     * @return boolean if the item was handled or not
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.quick_scan) {
            startActivity(new Intent(getContext(), ScanIsbnActivity.class));
            return true;
        }

        return false;
    }

    /**
     * Called when Fragment is resumed.
     * It rehydrates the data.
     */
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * Loads notifications from database.
     * Manipulates the views during loading.
     */
    private void loadData() {
        final ProgressBar loader = v.findViewById(R.id.loader);
        v.findViewById(R.id.noData).setVisibility(View.GONE);
        v.findViewById(R.id.requestList).setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Notifications");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notifications.clear();

                for (DataSnapshot ds : dataSnapshot.child(Globals.getInstance().user.getUid()).getChildren()) {
                    Notification r = ds.getValue(Notification.class);
                    notifications.add(r);
                }

                if(notifications.isEmpty()) {
                    v.findViewById(R.id.noData).setVisibility(View.VISIBLE);
                    v.findViewById(R.id.requestList).setVisibility(View.GONE);
                } else {
                    v.findViewById(R.id.noData).setVisibility(View.GONE);
                    v.findViewById(R.id.requestList).setVisibility(View.VISIBLE);
                }

                loader.setVisibility(View.GONE);
                Collections.reverse(notifications);
                ba.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}