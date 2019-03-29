package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;

/**
 * This class allows a user to view book information for a book that they own
 * Still need to add the book description
 * Still need to implement the set location so that a user can choose a location on the map
 * */

public class ViewBookActivity extends AppCompatActivity {
    private Book book;
    public ImageView image;
    public RecyclerView recyclerView;

    private String book_id = "";
    public Globals globals;
    private boolean hasImage = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_book);
        book_id = getIntent().getStringExtra("id");
        updateData(book_id);

        image = findViewById(R.id.bookImage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasImage) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popup = inflater.inflate(R.layout.image_pop_up, null);
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
                    } catch (ClassCastException e) {
                        Toast.makeText(ViewBookActivity.this, "No current picture", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // TODO: Click owner, go to view profile.

        globals = Globals.getInstance();


        Button locationButton = findViewById(R.id.location);

        if(book.getAcceptedRequest() != null &&
                (book.userIsOwner() ||
                        book.getAcceptedRequest().getUser().getUid().equals(Globals.getInstance().user.getUid()))
        ) {
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

        if(!book_id.isEmpty()) {
            updateData(book_id);
            return;
        }

        finish();
    }

    private void updateData(String id) {
        if(id != null) {
            book = Book.find(id);

            TextView title = findViewById(R.id.title);
            TextView author = findViewById(R.id.author);
            TextView isbn = findViewById(R.id.isbn);
            TextView owner = findViewById(R.id.owner);
            TextView description = findViewById(R.id.description);
            TextView status = findViewById(R.id.status);

            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            isbn.setText(Long.toString(book.getIsbn()));
            owner.setText(book.getOwner().getName());
            description.setText(book.getDescription());

            String str_status = book.getStatus();
            status.setText(str_status);
            final ImageView bookimage = findViewById(R.id.bookImage);

            FirebaseStorage.getInstance().getReference().child("Book images/"+id+"/bookimage.png").getBytes(Long.MAX_VALUE)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    if(bytes != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        bookimage.setImageBitmap(bitmap);
                        hasImage = true;
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    bookimage.setImageResource(R.drawable.ic_book_black_24dp);
                    Toast.makeText(ViewBookActivity.this, "test", Toast.LENGTH_SHORT).show();
                    hasImage = false;
                }
            });
        }
    }

}
