package ca.ualberta.c301w19t14.onebook.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.adapters.BookAdapter;
import ca.ualberta.c301w19t14.onebook.R;

/**
 * This class allows a user to do a key word search on a book title
 * Currently only allows keyword searches on title,
 * but we need to update it to include author, description, owner, and isbn
 * the search is currently case sensitive and we need to change that.
 * @author CMPUT301 Team14: Oran R
 * @version 1.0
 */
public class SearchingActivity extends AppCompatActivity {

    public BookAdapter ba;

    public ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Book> filteredBooks = new ArrayList<>();

    private String keyword = "";

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
     * @param savedInstanceState: last possible state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_main);

        RecyclerView recyclerView = findViewById(R.id.bookList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        ba = new BookAdapter(this, filteredBooks, true);
        recyclerView.setAdapter(ba);

        loadData();

    }

    /**
     *
     * @param menu: from the selected activity actionbar
     * @return a check so the method won't stop
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searching_menu, menu);

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                keyword = newText;
                filterData();
                return true;
            }
        });

        return true;
    }

    /**
     *
     * @param item: filter selecred
     * @return the state of the item if will exist
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        } else if (id == R.id.quick_filter) {
            AlertDialog.Builder fBuilder = new AlertDialog.Builder(this);

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
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method loads borrowed books from database.
     * Manipulates the views during loading.
     */
    private void loadData() {
        final ProgressBar loader = findViewById(R.id.loader);
        findViewById(R.id.noData).setVisibility(View.GONE);
        findViewById(R.id.bookList).setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Books");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                books.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Book r = ds.getValue(Book.class);
                    books.add(r);
                }

                loader.setVisibility(View.GONE);
                filterData();
                ba.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * This methods filters through the data as a search is happening.
     *
     */
    private void filterData() {
        filteredBooks.clear();
        ArrayList<String> statuses = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            if(checkedFilters[i]) {
                statuses.add(filterOptions[i]);
            }
        }

        for(Book b : books) {
            String check = (b.getTitle() + b.getAuthor() + Long.toString(b.getIsbn()) + b.getDescription()).toLowerCase();
            Log.e("d", check);

            if(statuses.contains(b.status()) && check.contains(keyword.toLowerCase())) {
                filteredBooks.add(b);
            }
        }

        if(filteredBooks.isEmpty()) {
            findViewById(R.id.noData).setVisibility(View.VISIBLE);
            findViewById(R.id.bookList).setVisibility(View.GONE);
        } else {
            findViewById(R.id.noData).setVisibility(View.GONE);
            findViewById(R.id.bookList).setVisibility(View.VISIBLE);
        }

        ba.notifyDataSetChanged();
    }

}
