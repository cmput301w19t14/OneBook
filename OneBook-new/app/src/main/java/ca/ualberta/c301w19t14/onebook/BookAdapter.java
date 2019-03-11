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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public View view;
    private ArrayList<Book> bookList;
    public Context mContext;
    public Boolean mode;


    public BookAdapter(Context context, ArrayList<Book> bookList, Boolean mode) {
        this.bookList = bookList;
        this.mContext = context;
        this.mode = mode;
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public void onBindViewHolder(BookViewHolder contactViewHolder, int i) {


        Book book = bookList.get(i);
        contactViewHolder.book = book;

        contactViewHolder.vTitle.setText(book.getTitle());
        contactViewHolder.vOwner.setText(book.getOwner().getName());
        contactViewHolder.vStatus.setText(book.getStatus());

        //MODE = true -> do borrowing/lending
        if (mode) {
            // If status is Accepted or Requested, color code
            // otherwise, leave black if Borrowed
            if (book.getStatus().equals("Accepted"))
                contactViewHolder.vStatus.setTextColor(Color.GREEN);
            else if (book.getStatus().equals("Requested"))
                contactViewHolder.vStatus.setTextColor(Color.YELLOW);
        }
        else {
            // If status


        }
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
        public Book book;

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
                    Bundle bundle = new Bundle();
                    bundle.putString("id", book.getId());

                    String current_user = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                    if (current_user.equals(book.getOwner().getEmail()))
                    {
                        Intent intent = new Intent(mContext, ViewBookActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(mContext, ViewRequestableActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }

                }
            });
        }
    }
}