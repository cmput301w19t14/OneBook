package ca.ualberta.c301w19t14.onebook.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

//import de.hdodenhof.circleimageview.CircleImageView;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.activities.ViewBookActivity;
import ca.ualberta.c301w19t14.onebook.models.Book;

/**This class is used to create all of the recycler views that list books.
 This includes the Search Book View, the Lending View, and the Borrowing View.
 * @author CMPUT 301 Team 14
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public View view;
    private ArrayList<Book> bookList;
    public Context mContext;
    public Boolean mode;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference;

    /**
     *
     * @param context
     * @param bookList
     * @param mode
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
     * @param bookVh
     * @param i
     */
    @Override
    public void onBindViewHolder(final BookViewHolder bookVh, int i) {

        Book book = bookList.get(i);
        bookVh.book = book;


        bookVh.vTitle.setText(book.getTitle());
        bookVh.vOwner.setText(book.getOwner().getName());
        bookVh.vStatus.setText(book.getStatus().toUpperCase());
        bookVh.vAuthor.setText(book.getAuthor());
        try {
            storage.getReference().child("Book images/" + book.getId() + "/bookimage.png").getBytes(Long.MAX_VALUE)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bookVh.vImage.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        catch(Exception e)
        {
            Log.d("Book adapter", "onBindViewHolder: no image");
        }
        //bookVh.vImage.setImageBitmap();


        /*
        //MODE = true -> do borrowing/lending
        if (mode) {
            // If status is Accepted or Requested, color code
            // otherwise, leave black if Borrowed
            if (book.getStatus().equals("Accepted"))
                bookVh.vStatus.setTextColor(Color.GREEN);
            else if (book.getStatus().equals("Requested"))
                bookVh.vStatus.setTextColor(Color.YELLOW);
        }
        else {
            // If status is borrowed, do nothing.
        }*/
    }

    /**
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.book_list_item, viewGroup, false);

        return new BookViewHolder(itemView);
    }

    /**
     *
     */
    public class BookViewHolder extends RecyclerView.ViewHolder {
        protected TextView vStatus;
        protected TextView vOwner;
        protected TextView vAuthor;
        protected TextView vTitle;
        //protected CircleImageView vImage;
        public Book book;

        /**
         *
         * @param v
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

                    //DEBUG - remove later
                    Toast toast = Toast
                            .makeText(mContext, "Book ID: " + book.getTitle(), Toast.LENGTH_SHORT);
                    toast.show();

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
