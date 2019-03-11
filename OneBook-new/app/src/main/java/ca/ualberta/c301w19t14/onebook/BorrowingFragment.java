package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;
import ca.ualberta.c301w19t14.onebook.util.GeneralUtil;

public class BorrowingFragment extends Fragment {

    View myView;
    GeneralUtil util;
    Globals globals;
    public ArrayList<Book> books = new ArrayList<Book>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.borrowing_main,container, false);

        //get globals
        globals = Globals.getInstance();

        RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.borrow_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        books = globals.firebaseUtil.getAllBooks();
        books = util.findBorrowerBooks(books);
        BookAdapter ba = new BookAdapter(getActivity(), books, true);
        recyclerView.setAdapter(ba);

        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton search_button = view.findViewById(R.id.searchButton);
        ArrayList<Book> user_books = new ArrayList<Book>();

        util = new GeneralUtil();

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(getContext(), SearchingActivity.class);
                startActivity(intent);
            }
        });
    }



}
