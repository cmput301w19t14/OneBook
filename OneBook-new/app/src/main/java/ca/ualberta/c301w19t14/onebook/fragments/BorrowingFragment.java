package ca.ualberta.c301w19t14.onebook.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import ca.ualberta.c301w19t14.onebook.activities.ScanIsbnActivity;
import ca.ualberta.c301w19t14.onebook.adapters.BookAdapter;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.activities.SearchingActivity;
import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.models.Notification;
import ca.ualberta.c301w19t14.onebook.util.GeneralUtil;

/**
 * Shows the current books a user is borrowing.
 *
 * @author Dustin, Ana, Dimitri
 */
public class BorrowingFragment extends Fragment {

    public View v;
    public ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<Book> filteredBooks = new ArrayList<>();

    private BookAdapter ba;

    String[] filterOptions = new String[] {
            "Available",
            "Borrowed",
            "Requested",
            "Accepted",
    };
    public static boolean[] checkedFilters = new boolean[]{
            true,
            true,
            true,
            true
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
        v = inflater.inflate(R.layout.borrowing_main, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.bookList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        ba = new BookAdapter(getActivity(), filteredBooks, true);
        recyclerView.setAdapter(ba);

        loadData();

        setHasOptionsMenu(true);

        // go to the search page when the search button is clicked
        v.findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchingActivity.class));
            }
        });

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
     *
     * @param menu options menu
     * @param inflater MenuInflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_toolbar, menu);
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
     * Loads borrowed books from database.
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
                    if(r.getBorrower() != null && r.getBorrower().getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
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

    private void filterData() {
        filteredBooks.clear();
        ArrayList<String> statuses = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
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
