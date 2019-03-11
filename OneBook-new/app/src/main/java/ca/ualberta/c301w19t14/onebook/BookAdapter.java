package ca.ualberta.c301w19t14.onebook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public View view;
    private ArrayList<Book> bookList;
    public Context mContext;



    public BookAdapter(Context context, ArrayList<Book> bookList) {
        this.bookList = bookList;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public void onBindViewHolder(BookViewHolder contactViewHolder, int i) {


        Book book = bookList.get(i);

        //variables for the book associated with card
        contactViewHolder.owner = book.getOwner().getEmail();
        contactViewHolder.ISBN = book.getIsbn();
        contactViewHolder.title = book.getTitle();
        contactViewHolder.author = book.getAuthor();
        contactViewHolder.description = book.getDescription();
        contactViewHolder.status = book.getStatus();

        contactViewHolder.vTitle.setText(book.getTitle());
        contactViewHolder.vOwner.setText(book.getOwner().getName());
        contactViewHolder.vStatus.setText(book.getStatus());

        // If status is Accepted or Requested, color code
        // otherwise, leave black if Borrowed
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
        public long ISBN;
        public String owner;
        public String title;
        public String author;
        public String description;
        public String status;

        public BookViewHolder(View v) {
            super(v);


            v.setBackgroundColor(Color.LTGRAY);
            vTitle =  (TextView) v.findViewById(R.id.txtTitle);
            vOwner = (TextView)  v.findViewById(R.id.txtOwner);
            vStatus = (TextView)  v.findViewById(R.id.txtStatus);

            //make the cards clickable
            view = v;
            view.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v){
                    Log.d("AHHHHH", "Onclick: clicking recycle view");


                    Toast.makeText(mContext, String.valueOf(ISBN), Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putLong("ISBN",ISBN);
                    bundle.putString("DESCRIPTION",description);
                    bundle.putString("STATUS",status);
                    bundle.putString("TITLE",title);
                    bundle.putString("AUTHOR",author);
                    bundle.putString("OWNER",owner);

                    Log.d("book adapter", "all the info: "+ISBN+description+status+title+author+owner);
                    Intent intent = new Intent(mContext, ViewBookActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);


                }
            });
        }
    }
}