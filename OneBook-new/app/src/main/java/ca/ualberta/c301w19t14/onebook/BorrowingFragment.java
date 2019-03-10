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

    //*********************************************************************************************
    // This is some fabricated test data to make sure the activity page works

    public Borrower john = new Borrower("316", "John Deere", "john@gmail.com");
    public Borrower mary = new Borrower("111", "Mary Anne", "mary@gmail.com");
    public Borrower sue = new Borrower("696", "Sue King", "sue@gmail.com");
    public Borrower gregg = new Borrower("204", "Gregg Or", "gregg@gmail.com");

    public Book narnia = new Book("Narnia", john, sue);
    public Book bible = new Book("Bible", john, gregg);
    public Book cars = new Book("Cars", mary, john);
    public Book marketing = new Book("Marketing", gregg, mary);
    public Book finance = new Book("Finance", gregg, sue);

    public ArrayList<Book> books = new ArrayList<Book>();


    //*********************************************************************************************


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.borrowing_main,container, false);

        RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.borrow_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        BookAdapter ba = new BookAdapter(getActivity(), books);
        recyclerView.setAdapter(ba);

        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton search_button = view.findViewById(R.id.searchButton);
        ArrayList<Book> user_books = new ArrayList<Book>();
        util = new GeneralUtil();



        //*****************************************************************************************
        //more for recycleview testing

        narnia.setStatus("Accepted");
        bible.setStatus("Borrowed");
        cars.setStatus("Requested");
        marketing.setStatus("Borrowed");
        finance.setStatus("Accepted");

        books.add(narnia);
        books.add(bible);
        books.add(cars);
        books.add(marketing);
        books.add(finance);

        //*****************************************************************************************
        user_books = util.findBorrowerBooks(books, sue);


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(), SearchingActivity.class);
                startActivity(intent);
            }
        });
    }



}
