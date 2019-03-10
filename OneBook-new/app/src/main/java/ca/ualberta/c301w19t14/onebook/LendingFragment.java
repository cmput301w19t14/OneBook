package ca.ualberta.c301w19t14.onebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class LendingFragment extends Fragment {

    View myView;
    FirebaseRecyclerAdapter adapter;
    ArrayList<Book> book;

    LendingFragment(ArrayList<Book> book) {
        super();
        this.book = book;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.lending_main,container, false);

        RecyclerView mRecyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        BookAdapter ba = new BookAdapter(this.book);
        mRecyclerView.setAdapter(ba);

        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
