package ca.ualberta.c301w19t14.onebook;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AddActivity extends AppCompatActivity {
    public ImageView image;

    private static final String TAG = "add activity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add);
        image = findViewById(R.id.bookPhoto);
        storageReference = storage.getReference();



        //byte[] data = baos



        Intent intent = getIntent();
        if (intent != null) {
            String isbnString = intent.getStringExtra("ISBN");
            EditText isbn = (EditText) findViewById(R.id.isbn);
            isbn.setText(isbnString);
        }

        final User user = Globals.getInstance().users.getData().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class);

        Button addPhoto = findViewById(R.id.addPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 1);

                }
                else
                {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
        Button btn = findViewById(R.id.add);

        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Books");
                        String name = myRef.push().getKey();
                        EditText title = (EditText) findViewById(R.id.title);
                        EditText author = (EditText) findViewById(R.id.author);
                        EditText isbn = (EditText) findViewById(R.id.isbn);
                        EditText desc = (EditText) findViewById(R.id.description);

                        Book book = new Book(Long.parseLong(isbn.getText().toString()), title.getText().toString(), author.getText().toString(), desc.getText().toString(), user);
                        book.setId(name);
                        myRef.child(name).setValue(book);

                        final StorageReference personRef = storageReference.child("Book images/"+name+"/bookimage.png");


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap imageBitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    imageBitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                                    byte[] byteArray = baos.toByteArray();
                                    UploadTask uploadTask = personRef.putBytes(byteArray);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AddActivity.this, "failed data commit", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                            Toast.makeText(AddActivity.this, "Data commited", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                catch(Exception e)
                                {
                                    Toast.makeText(AddActivity.this, "no image committed", Toast.LENGTH_SHORT).show();
                                }
                                //Bitmap imageBitmap = Bitmap.createBitmap(((BitmapDrawable)image.getDrawable()).getBitmap());



                            }
                        });

                        finish();
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
                Log.d(TAG, "onActivityResult: sucessful return to intent");
                Bundle extras = data.getExtras();
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
                image.setImageBitmap(imageBitmap);

            }
        }
    }

}
