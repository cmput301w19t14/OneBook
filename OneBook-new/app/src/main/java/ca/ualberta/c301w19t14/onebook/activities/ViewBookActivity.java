package ca.ualberta.c301w19t14.onebook.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.Request;

/**
 * This class implement a view book information page.
 * Shows all the descriptors for the book and allows updating by the owner
 * for each of the descriptors.
 *
 * @author CMPUT301 Team14: Dimitri T, Oran R, Anastasia B, Natalie H.
 * @version 1.0
 */

public class ViewBookActivity extends AppCompatActivity {
    private Book globalBook;
    public ImageView image;
    public RecyclerView recyclerView;

    private boolean isDeleted = false;
    private String book_id = null;
    private boolean hasImage = false;

    /**
     * Initializes the view.
     * @param savedInstanceState: last possible state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(book_id == null) {
            book_id = getIntent().getStringExtra("id");
        }

        updateData(book_id);
        invalidateOptionsMenu();

        image = findViewById(R.id.bookImage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasImage) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popup = inflater.inflate(R.layout.image_pop_up, null);
                    ImageView picture = popup.findViewById(R.id.ImageCloseUp);
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
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        if(!book_id.isEmpty()) {
            updateData(book_id);
            invalidateOptionsMenu();
            return;
        }

        finish();
    }

    /**
     * This method will display all the book's descriptors at the moment.
     * @param id: the book's id. Referred by in the database.
     */
    private void updateData(final String id) {
        if(id != null) {
            final ProgressBar loader = findViewById(R.id.loader);
            loader.setVisibility(View.VISIBLE);

            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference ref = db.getReference("Books").child(id);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(isDeleted) {
                        return;
                    }

                    final Book book = dataSnapshot.getValue(Book.class);
                    globalBook = book;
                    invalidateOptionsMenu();
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
                    loader.setVisibility(View.GONE);

                    String str_status = book.status();
                    status.setText(str_status);
                    final ImageView bookimage = findViewById(R.id.bookImage);

                    FirebaseStorage.getInstance().getReference().child("Book images/" + id + "/bookimage.png").getBytes(Long.MAX_VALUE)
                            .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    if (bytes != null) {
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        bookimage.setImageBitmap(bitmap);
                                        hasImage = true;
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            hasImage = false;
                        }
                    });
                    LinearLayout ownerinfo = findViewById(R.id.ownerInfo);
                    ownerinfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (globalBook != null) {

                                Intent intent = new Intent(ViewBookActivity.this, UserAccountActivity.class);
                                Bundle bundle = new Bundle();

                                bundle.putString("ID", globalBook.getOwner().getUid());
                                bundle.putString("NAME", globalBook.getOwner().getName());
                                bundle.putString("EMAIL", globalBook.getOwner().getEmail());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    });

                    Button locationButton = findViewById(R.id.location);

                    if (book.acceptedRequest() != null &&
                            (book.userIsOwner() ||
                                    book.acceptedRequest().getUser().getUid().equals(Globals.getInstance().user.getUid()))
                    ) {
                        locationButton.setVisibility(View.VISIBLE);

                        locationButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ViewBookActivity.this, MapsActivity.class);
                                intent.putExtra("book_id", book.getId());
                                // TODO: Show snackbar after location set
                                startActivityForResult(intent, 1);
                            }
                        });
                    } else {
                        locationButton.setVisibility(View.GONE);
                    }

                    Button requestsButton = findViewById(R.id.requests);
                    if (book.userIsOwner()) {
                        requestsButton.setVisibility(View.VISIBLE);

                        requestsButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ViewBookActivity.this, ViewRequestsActivity.class);
                                intent.putExtra("id", book.getId());
                                startActivity(intent);
                            }
                        });
                    } else {
                        requestsButton.setVisibility(View.GONE);
                    }

                    final Button requestButton = findViewById(R.id.request);
                    if (book.userCanRequest()) {
                        requestButton.setVisibility(View.VISIBLE);

                        requestButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Request.requestBook(Globals.getCurrentUser(), book);
                                requestButton.setVisibility(View.GONE);
                                findViewById(R.id.divider7).setVisibility(View.GONE);
                                Snackbar.make(findViewById(R.id.viewBook), "Book requested.", Snackbar.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        requestButton.setVisibility(View.GONE);
                        findViewById(R.id.divider7).setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    /**
     *
     * @param menu: the activity's layout
     * @return checks for a success
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(globalBook != null && globalBook.userIsOwner()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param menu: the activity's layout
     * @return checks for a success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_book, menu);
        return true;
    }

    /**
     *
     * @param item: the item selected from the given options
     * @return a check for success
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.editIcon:
                Intent edit = new Intent(this, EditBookActivity.class);
                edit.putExtra("id", globalBook.getId());
                startActivityForResult(edit, 1);
                break;
            case R.id.deleteIcon:
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Confirm Book Deletion");
                alertDialog.setMessage("Please confirm you would like to delete this book.");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // update request location
                                isDeleted = true;
                                dialog.dismiss();
                                globalBook.delete();
                                finish();

                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}