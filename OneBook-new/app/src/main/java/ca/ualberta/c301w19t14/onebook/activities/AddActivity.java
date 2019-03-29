package ca.ualberta.c301w19t14.onebook.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.models.User;

/**
 * Add a new book.
 *
 * @author Oran R, Dimitri T
 */
public class AddActivity extends AppCompatActivity {
    public ImageView image;

    private static final String TAG = "add activity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);
        image = findViewById(R.id.bookPhoto);
        storageReference = storage.getReference();

        Intent intent = getIntent();

        if (intent != null) {
            String isbnString = intent.getStringExtra("ISBN");
            EditText isbn = (EditText) findViewById(R.id.isbn);
            isbn.setText(isbnString);
        }

        final User user = Globals.getCurrentUser();


        Button btn = findViewById(R.id.add);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Books");

                        Book book = new Book(Long.parseLong(stringFromEditText(findViewById(R.id.isbn))),
                                stringFromEditText(findViewById(R.id.title)),
                                stringFromEditText(findViewById(R.id.author)),
                                stringFromEditText(findViewById(R.id.description)),
                                user);

                        book.setId(myRef.push().getKey());
                        myRef.child(book.getId()).setValue(book);

                        final StorageReference personRef = storageReference.child("Book images/" + book.getId() + "/bookimage.png");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap imageBitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                    byte[] byteArray = baos.toByteArray();
                                    UploadTask uploadTask = personRef.putBytes(byteArray);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        }
                                    });
                                } catch (Exception e) {
                                    // failed
                                }
                            }
                        });

                        finish();
                    }
                });

        Button addPhoto = findViewById(R.id.addPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddActivity.this,
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
                image.setAlpha(1);

            }
        }
    }

    private String stringFromEditText(View e) {
        return ((EditText) e).getText().toString();
    }
}
