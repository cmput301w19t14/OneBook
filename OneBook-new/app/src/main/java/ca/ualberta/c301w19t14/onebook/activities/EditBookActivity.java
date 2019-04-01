package ca.ualberta.c301w19t14.onebook.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

/**
 * This class allows edits/updates to books that have been inputted into the database.
 * @author CMPUT301 Team14: Oran R, Dimitri T, Anastasia B.
 * @version 1.0
 * @see ViewBookActivity {@link #onOptionsItemSelected}
 */
public class EditBookActivity extends AppCompatActivity {

    private final static int REQUEST_IMAGE_CAPTURE = 1;
    private EditText title;
    private EditText author;
    private EditText isbn;
    private Button editphoto;

    private ImageView image;
    private EditText description;
    public FirebaseUtil books;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference book_ref;

    /**
     * Initializes the view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_book_main);

        Intent intent = getIntent();

        image = findViewById(R.id.bookPhoto);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        isbn = findViewById(R.id.isbn);
        description = findViewById(R.id.description);

        final ProgressBar loader = findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Books").child(intent.getStringExtra("id"));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final Book book = dataSnapshot.getValue(Book.class);
                title.setText(book.getTitle());
                author.setText(book.getAuthor());
                isbn.setText(Long.toString(book.getIsbn()));
                description.setText(book.getDescription());

                book_ref = storage.getReference().child("Book images/"+book.getId()+"/bookimage.png");

                // set the current data

                book_ref.getBytes(Long.MAX_VALUE)
                        .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                image.setImageBitmap(bitmap);
                                image.setImageAlpha(255);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override public void onFailure(@NonNull Exception e) { }});

                editphoto = findViewById(R.id.addPhoto);
                editphoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(EditBookActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(EditBookActivity.this,
                                    new String[]{Manifest.permission.CAMERA}, 1);
                        }
                        else {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                            }
                        }
                    }
                });

                Button saveButton =  findViewById(R.id.save);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        book.setAuthor(author.getText().toString());
                        book.setTitle(title.getText().toString());
                        book.setDescription(description.getText().toString());
                        book.setIsbn(Long.valueOf(isbn.getText().toString()));
                        book.update();
                        //committing image to fire base storage
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    loader.setVisibility(View.VISIBLE);
                                    Bitmap imageBitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                    byte[] byteArray = baos.toByteArray();
                                    UploadTask uploadTask = book_ref.putBytes(byteArray);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            finish();
                                        }
                                    });


                                }
                                catch(ClassCastException e)
                                {
                                    book_ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                                }
                            }
                        });

                    }
                });

                loader.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            if(requestCode == REQUEST_IMAGE_CAPTURE)
            {
                Bundle extras = data.getExtras();
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
                image.setImageBitmap(imageBitmap);
                image.setImageAlpha(255);
            }
        }
    }

    /**
     * For when the navigation item was selected.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //https://stackoverflow.com/questions/38438619/how-to-return-to-previous-fragment-by-clicking-backnot-hardware-back-button

        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
