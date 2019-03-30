package ca.ualberta.c301w19t14.onebook.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import ca.ualberta.c301w19t14.onebook.adapters.BookAdapter;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.activities.AddActivity;
import ca.ualberta.c301w19t14.onebook.activities.ScanIsbnActivity;
import ca.ualberta.c301w19t14.onebook.models.Book;

/**
 * Shows the current books a user owns.
 *
 * @author Ana, Dimitri
 */
public class LendingFragment extends Fragment {

    private View v;
    private BookAdapter ba;
    private ArrayList<Book> books = new ArrayList<>();
    private Globals globals;
    private RecyclerView mRecyclerView;

    ArrayList<Integer> mUserItems = new ArrayList<>();

    private String[] filterOptions = new String[] {
            "Available",
            "Borrowed",
            "Requested"
    };
    private static boolean[] checkedFilters = new boolean[]{
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
        v = inflater.inflate(R.layout.lending_main, container, false);
        setHasOptionsMenu(true);

        mRecyclerView = v.findViewById(R.id.bookList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ba = new BookAdapter(getActivity(), books, true);
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

        final List<String> filtersList = Arrays.asList(filterOptions);

        if (id == R.id.quick_camera) {
            Intent intent = new Intent(getActivity(), ScanIsbnActivity.class);
            this.startActivity(intent);
        }
        else if (id == R.id.quick_filter) {
            AlertDialog.Builder fBuilder = new AlertDialog.Builder(this.getContext());

            final boolean[] checkedFiltersOriginal = new boolean[3];
            System.arraycopy(checkedFilters, 0, checkedFiltersOriginal, 0, checkedFilters.length);

            fBuilder.setMultiChoiceItems(filterOptions, checkedFilters, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked) {
                        if (!mUserItems.contains(which)) {
                            mUserItems.add(which);
                        }
                    } else if (mUserItems.contains(which)) {
                        mUserItems.remove(which);
                    }
                }
            });

            fBuilder.setCancelable(false);
            fBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkedFilters = checkedFiltersOriginal;
                    dialog.dismiss();
                }
            });

            fBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // clear Recycler View
                    final int size = books.size();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            books.remove(0);
                        }
                        ba.notifyItemRangeRemoved(0, size);
                    }

                    // update Recycler View
                    globals = Globals.getInstance();
                    ArrayList<Book> deltabook = new ArrayList<Book>();

                    for(DataSnapshot snapshot : globals.books.getData().getChildren()) {
                        Book book = snapshot.getValue(Book.class);

                        if(book.getOwner().getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                        {
                            deltabook.add(book);
                            for (int i = 0; i < size; i++) {
                                if (checkedFilters[i]) {
                                    String filter = filterOptions[i];
                                    if (book.getStatus().contains(filter) && (!deltabook.contains(book))) {
                                        deltabook.add(book);
                                    }
                                }
                                else if (!checkedFilters[i]) {
                                    String filter = filterOptions[i];
                                    if (book.getStatus().contains(filter) && (deltabook.contains(book))) {
                                        deltabook.remove(book);
                                    }
                                }
                            }
                        }
                    }
                    ba = new BookAdapter(getActivity(), deltabook, true);
                    mRecyclerView.setAdapter(ba);
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

                if(books.isEmpty()) {
                    v.findViewById(R.id.noData).setVisibility(View.VISIBLE);
                    v.findViewById(R.id.bookList).setVisibility(View.GONE);
                } else {
                    v.findViewById(R.id.noData).setVisibility(View.GONE);
                    v.findViewById(R.id.bookList).setVisibility(View.VISIBLE);
                }

                loader.setVisibility(View.GONE);
                ba.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
