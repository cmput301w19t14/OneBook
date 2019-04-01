package ca.ualberta.c301w19t14.onebook.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.activities.ViewBookActivity;
import ca.ualberta.c301w19t14.onebook.models.Book;

/**
 * This class is used to create all of the recycler views that list books.
 * Includes the Search Book View, the Lending View, and the Borrowing View.
 *
 * @author CMPUT301 Team14: Dustin, Dimitri, Oran
 * @version 1.0
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public View view;
    private ArrayList<Book> bookList;
    public Context mContext;
    public Boolean mode;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();

    /**
     *
     * @param context: the activity/fragment's state
     * @param bookList: the lists of books being used
     * @param mode: state of the book
     */
    public BookAdapter(Context context, ArrayList<Book> bookList, Boolean mode) {
        this.bookList = bookList;
        this.mContext = context;
        this.mode = mode;
    }

    /**
     *
     * @return bookList.size()
     */
    @Override
    public int getItemCount() {
        return bookList.size();
    }

    /**
     *
     * @param bookVh: BookViewHolder the bookList is going in.
     * @param i: index of the bookList
     */
    @Override
    public void onBindViewHolder(final BookViewHolder bookVh, int i) {

        Book book = bookList.get(i);
        bookVh.book = book;

        bookVh.vTitle.setText(book.getTitle());
        bookVh.vOwner.setText(book.getOwner().getName());
        bookVh.vStatus.setText(book.status().toUpperCase());
        bookVh.vAuthor.setText(book.getAuthor());
        bookVh.vImage.setImageResource(R.drawable.book64);
        StorageReference ref = storage.getReference().child("Book images/" + book.getId() + "/bookimage.png");
        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()) {
                    Glide.with(mContext).load(task.getResult()).placeholder(R.drawable.book64).into(bookVh.vImage);
                }
            }
        });

    }

    /**
     *
     * @param viewGroup: the view the bookList is going in
     * @param i: index from the bookList
     * @return BookViewHolder object containing the right book list
     */
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.book_list_item, viewGroup, false);

        return new BookViewHolder(itemView);
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        protected TextView vStatus;
        protected TextView vOwner;
        protected TextView vAuthor;
        protected TextView vTitle;
        protected CircleImageView vImage;
        public Book book;

        /**
         *
         * @param v: the view for a book
         */
        public BookViewHolder(View v) {
            super(v);
            vTitle =  (TextView) v.findViewById(R.id.bookTitle);
            vOwner = (TextView)  v.findViewById(R.id.bookOwner);
            vStatus = (TextView)  v.findViewById(R.id.bookStatus);
            vAuthor = (TextView)  v.findViewById(R.id.bookAuthor);
            vImage = v.findViewById(R.id.bookImage);

            //make the cards clickable
            view = v;
            view.setOnClickListener(new View.OnClickListener() {

                //create bundle to pass the current book to the view book page
                @Override public void onClick(View v){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", book.getId());

                    //if the user clicks on a book they own, they will get a view page that allows edits
                    //if the user clicks on a book they do not own, they will get a view page that doesn't allow edits
                    String current_user = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                        Intent intent = new Intent(mContext, ViewBookActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                }
            });
        }
    }
}
