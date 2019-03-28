package ca.ualberta.c301w19t14.onebook.fragements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.adapters.BookAdapter;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.activities.SearchingActivity;
import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.util.GeneralUtil;

/**This class runs when Borrowing is clicked on in the navigation menu
 * It displays a recycler view of all books that the current user has requested or has borrowed
 * @author CMPUT 301 Team 14*/
public class BorrowingFragment extends Fragment {

    public View myView;
    GeneralUtil util;
    Globals globals;
    public ArrayList<Book> books = new ArrayList<Book>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.borrowing_main,container, false);




        //get globals
        globals = Globals.getInstance();
        util = new GeneralUtil();

        //create recycler view of books requested or borrowed by current user
        RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.borrow_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        books = util.findBorrowerBooks();
        BookAdapter ba = new BookAdapter(getActivity(), books, true);
        recyclerView.setAdapter(ba);

        return myView;
    }

    //create page content
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        FloatingActionButton search_button = view.findViewById(R.id.searchButton);
        ArrayList<Book> user_books = new ArrayList<Book>();

        util = new GeneralUtil();

        //go to the search page when the search button is clicked
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(getContext(), SearchingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return false;
    }




}
