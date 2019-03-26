package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**This class runs when "Lending" is clicked on the navigation menu
 * It displays all of the books that the current user owns
 * If a user clicks on an item in a list, they see the view book page with an edit option
 * @author CMPUT 301 Team 14*/
public class LendingFragment extends Fragment {

    View myView;
    BookAdapter ba;
    ArrayList<Book> book;
    private Globals globals;
    private RecyclerView mRecyclerView;


    public LendingFragment() {

    }

    public LendingFragment(ArrayList<Book> book) {
        super();
        this.book = book;
    }

    //create recycler view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.lending_main,container, false);

        mRecyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ba = new BookAdapter(getActivity(), book, true);
        mRecyclerView.setAdapter(ba);

        myView.findViewById(R.id.newBook).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), AddActivity.class));
                    }
                }
        );

        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        //back button override
        //Source: https://stackoverflow.com/questions/7992216/android-fragment-handle-back-button-press
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();

        //set up listener for back button
        this.getView().setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        } );
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        //see if anything changed in the database
        globals = Globals.getInstance();
        ArrayList<Book> deltabook = new ArrayList<Book>();

        for(DataSnapshot snapshot : globals.books.getData().getChildren()) {
            Book b = snapshot.getValue(Book.class);

            if(b.getOwner().getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
            {
                deltabook.add(b);
            }
        }
        ba = new BookAdapter(getActivity(), deltabook, true);
        mRecyclerView.setAdapter(ba);
    }

    //use tool bar that has the camera button
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.camera_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return false;
    }

}
