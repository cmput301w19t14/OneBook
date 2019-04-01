package ca.ualberta.c301w19t14.onebook.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.ualberta.c301w19t14.onebook.adapters.BookAdapter;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.activities.AddActivity;
import ca.ualberta.c301w19t14.onebook.activities.ScanIsbnActivity;
import ca.ualberta.c301w19t14.onebook.models.Book;

/**
 * This fragment shows a list of the current books a user owns.
 * @author CMPUT301 Team14: Ana B, Dimitri T
 * @version 1.0
 */
public class LendingFragment extends Fragment {

    private View v;
    private BookAdapter ba;
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Book> filteredBooks = new ArrayList<>();
    private RecyclerView mRecyclerView;

    private String[] filterOptions = new String[] {
            "Available",
            "Accepted",
            "Borrowed",
    };

    private static boolean[] checkedFilters = new boolean[]{
            true,
            true,
            true,
    };

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
        v = inflater.inflate(R.layout.lending_main, container, false);
        setHasOptionsMenu(true);

        mRecyclerView = v.findViewById(R.id.bookList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ba = new BookAdapter(getActivity(), filteredBooks, true);
        mRecyclerView.setAdapter(ba);

        v.findViewById(R.id.newBook).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), AddActivity.class));
                    }
                }
        );

        return v;
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
     * Creates the options menu (top right).
     * @param menu options menu
     * @param inflater MenuInflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_toolbar, menu);
    }

    /**
     * Handles selecting an options menu item.
     * @param item android id of the item clicked
     * @return boolean if the item was handled or not
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.quick_camera) {
            Intent intent = new Intent(getActivity(), ScanIsbnActivity.class);
            this.startActivity(intent);
        }
        else if (id == R.id.quick_filter) {
            AlertDialog.Builder fBuilder = new AlertDialog.Builder(this.getContext());

            fBuilder.setMultiChoiceItems(filterOptions, checkedFilters, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    checkedFilters[which] = isChecked;
                }
            });

            fBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    filterData();
                }
            });

            fBuilder.setTitle("Filtering options available:");

            AlertDialog fDialog = fBuilder.create();
            fDialog.show();
        }

        return true;
    }

    /**
     * Loads owned books from database.
     * Manipulates the views during loading.
     */
    private void loadData() {
        final ProgressBar loader = v.findViewById(R.id.loader);
        v.findViewById(R.id.noData).setVisibility(View.GONE);
        v.findViewById(R.id.bookList).setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Books");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                books.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book r = ds.getValue(Book.class);
                    if(r.userIsOwner()) {
                        books.add(r);
                    }
                }


                loader.setVisibility(View.GONE);
                filterData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * This methods filters through the data as a search is happening.
     */
    private void filterData() {
        filteredBooks.clear();
        ArrayList<String> statuses = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            if(checkedFilters[i]) {
                statuses.add(filterOptions[i]);
            }
        }
        for(Book b : books) {
            if(statuses.contains(b.status())) {
                filteredBooks.add(b);
            }
        }

        if(filteredBooks.isEmpty()) {
            v.findViewById(R.id.noData).setVisibility(View.VISIBLE);
            v.findViewById(R.id.bookList).setVisibility(View.GONE);
        } else {
            v.findViewById(R.id.noData).setVisibility(View.GONE);
            v.findViewById(R.id.bookList).setVisibility(View.VISIBLE);
        }

        ba.notifyDataSetChanged();
    }

}
