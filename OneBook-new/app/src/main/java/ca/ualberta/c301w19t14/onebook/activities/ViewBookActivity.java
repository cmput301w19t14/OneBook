package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.ualberta.c301w19t14.onebook.BookRequestAdapter;
import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;

import static com.google.android.gms.vision.barcode.Barcode.ISBN;

/**
 * This class allows a user to view book information for a book that they own
 * Still need to add the book description
 * Still need to implement the set location so that a user can choose a location on the map
 * */

public class ViewBookActivity extends AppCompatActivity {
//need to add description update

    private TextView title;
    private TextView author;
    private TextView isbn;
    private TextView owner;
    private TextView description;
    private TextView status;
    private Book book;
    public ImageView image;
    public RecyclerView recyclerView;

    private String book_id = "";
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference;
    public Globals globals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_book_main);
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        book_id = bundle.getString("id");
        updateData(book_id);

        image = findViewById(R.id.bookImage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewBookActivity.this, "error caught", Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                View popup = inflater.inflate(R.layout.image_pop_up,null);
                ImageView picture = popup.findViewById(R.id.ImageCloseUp);
                try {
                    picture.setImageBitmap(((BitmapDrawable) image.getDrawable()).getBitmap());
                    int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                    int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                    final PopupWindow popupWindow = new PopupWindow(popup, width, height, true);
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                    popup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                }
                catch(ClassCastException e)
                {
                    Toast.makeText(ViewBookActivity.this,"No current picture",Toast.LENGTH_SHORT).show();
                }

            }
        });
        //let's the user click on an owner to see their profile
        TextView owner = (TextView)findViewById(R.id.bookOwner);
        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //when the user clicks on their own name as owner
                Intent intent = new Intent(ViewBookActivity.this, UserAccountActivity.class);
                intent.putExtras(bundle);
                ViewBookActivity.this.startActivity(intent);
            }
        });

        globals = Globals.getInstance();

        //Delete Button
        Button deleteButton = findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Books");
                myRef.child(book.getId()).removeValue();

                // TODO: MUST REMOVE REQUESTS
                finish();

            }
        });

        Button editButton =  findViewById(R.id.editBookButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewBookActivity.this, EditBookActivity.class);
                intent.putExtras(bundle);
                ViewBookActivity.this.startActivity(intent);
                updateData(book_id);
            }
        });

        Button locationButton = findViewById(R.id.GetLocationButton);

        if(book.getAcceptedRequest() != null &&
                (book.getOwner().getUid().equals(Globals.getInstance().user.getUid()) ||
                        book.getAcceptedRequest().getUser().getUid().equals(Globals.getInstance().user.getUid()))
        ) {
            // TODO: if accepted request and book owner OR if book.requests where requester = user, show location button.

            locationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ViewBookActivity.this, MapsActivity.class);
                    intent.putExtra("book_id", book.getId());
                    startActivity(intent);
                }
            });
        } else {
            locationButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        if (Globals.getInstance().books.getData().child(book_id).getValue(Book.class) == null) {
            finish();
            Log.d("DEBUG_ONEBOOK", "book is equal to null");
        }

        else if(!book_id.isEmpty()) {
            updateData(book_id);
        }
    }

    private void updateData(String id) {
        if(id != null) {
            title = findViewById(R.id.bookTitle);
            author = findViewById(R.id.bookauthor);
            isbn = findViewById(R.id.bookIsbn);
            owner = findViewById(R.id.bookOwner);
            description = findViewById(R.id.bookDescription);
            status = findViewById(R.id.bookStatus);
            recyclerView = findViewById(R.id.RequestView);
            LinearLayoutManager llm = new LinearLayoutManager(ViewBookActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);


            book = Globals.getInstance().books.getData().child(id).getValue(Book.class);

            String str_title = "Title: " + book.getTitle();
            title.setText(str_title);

            String str_author = "Author: " + book.getAuthor();
            author.setText(str_author);

            String str_ISBN = "ISBN: " + Long.toString(book.getIsbn());
            isbn.setText(str_ISBN);

            String str_owner = "Owner: " + book.getOwner().getName();
            owner.setText(str_owner);

            String str_description = "Description: " + book.getDescription();
            description.setText(str_description);

            String str_status = "Status: " + book.getStatus();
            status.setText(str_status);
            final ImageView bookimage = findViewById(R.id.bookImage);

            storage.getReference().child("Book images/"+id+"/bookimage.png").getBytes(Long.MAX_VALUE)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    bookimage.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            //BookRequestAdapter bookRequestAdapter = new BookRequestAdapter(ViewBookActivity.this,book.getRequest());
            //recyclerView.setAdapter(bookRequestAdapter);

            // otherwise book doesn't exist

        }
    }

}
