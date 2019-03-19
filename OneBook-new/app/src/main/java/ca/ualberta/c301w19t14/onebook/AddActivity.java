package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
        image = (ImageView)findViewById(R.id.bookPhoto);
        storageReference = storage.getReference();
        StorageReference personRef = storageReference.child("Book images/test directory");



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
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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

                        //StorageReference personRef = storageReference.child("Book images/");
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
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                image.setImageBitmap(imageBitmap);

            }
        }
    }
}
