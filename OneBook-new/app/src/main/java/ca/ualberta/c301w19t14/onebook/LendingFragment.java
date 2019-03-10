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

public class LendingFragment extends Fragment {

    View myView;
    FirebaseRecyclerAdapter adapter;

    public static class BookHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView author;

        BookHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ONEBOOK", "Element " + getAdapterPosition() + " clicked.");
                }
            });

            title = (TextView) v.findViewById(R.id.bookTitle);
            author = (TextView) v.findViewById(R.id.bookAuthor);
        }

        public TextView getTitle() {
            return title;
        }
        public TextView getAuthor() {
            return author;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.lending_main,container, false);

        RecyclerView mRecyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Books");

        Log.i("TAG", query.toString());

        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>()
                        .setQuery(query, Book.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Book, BookHolder>(options) {
            @Override
            public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.book_list_item, parent, false);

                return new BookHolder(view);
            }

            @Override
            protected void onBindViewHolder(BookHolder holder, int position, Book model) {
                Log.i("TEST", "TEST LOG");
                holder.getAuthor().setText(model.getAuthor());
                holder.getTitle().setText(model.getTitle());
            }
        };

        mRecyclerView.setAdapter(adapter);

        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
