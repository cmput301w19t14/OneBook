package ca.ualberta.c301w19t14.onebook;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<Book> bookList;


    public BookAdapter(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public void onBindViewHolder(BookViewHolder contactViewHolder, int i) {
        Book book = bookList.get(i);
        contactViewHolder.vTitle.setText(book.getTitle());
        contactViewHolder.vOwner.setText(book.getOwner().getName());
        contactViewHolder.vStatus.setText(book.getStatus());

        //If status is Accepted or Requested, color code
        //otherwise, leave black if Borrowed
        if (book.getStatus().equals("Accepted"))
            contactViewHolder.vStatus.setTextColor(Color.GREEN);
        else if (book.getStatus().equals("Requested"))
            contactViewHolder.vStatus.setTextColor(Color.YELLOW);
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.borrower_listitem, viewGroup, false);

        return new BookViewHolder(itemView);
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        protected TextView vStatus;
        protected TextView vOwner;
        protected TextView vTitle;

        public BookViewHolder(View v) {
            super(v);
            v.setBackgroundColor(Color.LTGRAY);
            vTitle =  (TextView) v.findViewById(R.id.txtTitle);
            vOwner = (TextView)  v.findViewById(R.id.txtOwner);
            vStatus = (TextView)  v.findViewById(R.id.txtStatus);
        }
    }

}