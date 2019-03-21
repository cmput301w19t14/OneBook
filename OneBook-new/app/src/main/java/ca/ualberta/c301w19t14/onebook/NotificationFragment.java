package ca.ualberta.c301w19t14.onebook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.platforminfo.GlobalLibraryVersionRegistrar;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.util.GeneralUtil;

/**This class will be used in part 5 to implement notifications
 * A notification will be sent to an owner when someone requests a book they own, or when someone returns it
 * A notification will be sent to a borrower when their request has been accepted or declined
 * @author CMPUT 301 Team 14*/
public class NotificationFragment extends Fragment {

    View myView;
    GeneralUtil util;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return myView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_home,container, false);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Notifications");

        ref.addValueEventListener(new ValueEventListener() {

            /**
             *
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Notification> notifications = new ArrayList<Notification>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Notification r = ds.getValue(Notification.class);
                    if(r.getUser().getEmail().equals(Globals.getInstance().user.getEmail())) {
                        notifications.add(r);
                    }
                }

                RecyclerView mRecyclerView = (RecyclerView) myView.findViewById(R.id.requestList);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);

                NotificationsAdapter ba = new NotificationsAdapter(getActivity(), notifications);
                mRecyclerView.setAdapter(ba);
                if(notifications.isEmpty()) {
                    myView.findViewById(R.id.noData).setVisibility(View.VISIBLE);
                    myView.findViewById(R.id.requestList).setVisibility(View.GONE);
                } else {
                    myView.findViewById(R.id.noData).setVisibility(View.GONE);
                    myView.findViewById(R.id.requestList).setVisibility(View.VISIBLE);
                }
            }

            /**
             *
             * @param databaseError
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return myView;
    }

    /**
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.camera_toolbar, menu);
    }

    /**
     *
     * @param item
     * @return false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return false;
    }

    /**
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
    }

}