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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

/**
 * Edit book activity.
 *
 * @author Oran, Dimitri
 */
public class EditBookActivity extends AppCompatActivity {

    private final static int REQUEST_IMAGE_CAPTURE = 1;
    private EditText title;
    private EditText author;
    private EditText isbn;
    private TextView owner;
    private Button editphoto;
    private Button editDelete;

    private ImageView image;
    private EditText description;
    public FirebaseUtil books;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference book_ref;

    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_book_main);

        Intent intent = getIntent();
        book = Book.find(intent.getStringExtra("id"));


        image = findViewById(R.id.bookImage);
        // init the views
        title = findViewById(R.id.editBookTitle);
        author = findViewById(R.id.editBookAuthor);
        isbn = findViewById(R.id.editBookISBN);
        TextView owner = findViewById(R.id.viewBookOwner);
        description = findViewById(R.id.editBookDescription);


        final Bundle bundle = intent.getExtras();
        final Book book = Globals.getInstance().books.getData().child(bundle.getString("id")).getValue(Book.class);
        book_ref = storage.getReference().child("Book images/"+book.getId()+"/bookimage.png");
        // set the current data
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(Long.toString(book.getIsbn()));
        owner.setText(book.getOwner().getName());
        description.setText(book.getDescription());

        book_ref.getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        image.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override public void onFailure(@NonNull Exception e) { }});
        editDelete = findViewById(R.id.delete_botton);
        editDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ic_account_box_black_24dp);
                //Drawable drawable = getResources().getDrawable(R.drawable.ic_account_box_black_24dp);
                //Bitmap i = ((BitmapDrawable) drawable).getBitmap();
                image.setImageResource(R.drawable.ic_account_box_black_24dp);
                //image.setImageBitmap(i);

            }
        });

        editphoto = findViewById(R.id.editPhoto);
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


        Button saveButton =  findViewById(R.id.saveBookButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                book.setAuthor(author.getText().toString());
                book.setTitle(title.getText().toString());
                book.setDescription(description.getText().toString());
                book.setIsbn(Long.valueOf(isbn.getText().toString()));
                book.update();

                finish();
                //committing image to fire base storage
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Bitmap imageBitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            byte[] byteArray = baos.toByteArray();
                            UploadTask uploadTask = book_ref.putBytes(byteArray);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditBookActivity.this, "failed data commit", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                    Toast.makeText(EditBookActivity.this, "Data commited", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        catch(ClassCastException e)
                        {
                            book_ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditBookActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditBookActivity.this, "Data is still there idiot", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

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
                //Log.d(TAG, "onActivityResult: sucessful return to intent");
                Bundle extras = data.getExtras();
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
                image.setImageBitmap(imageBitmap);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // for up navigation
        //https://stackoverflow.com/questions/38438619/how-to-return-to-previous-fragment-by-clicking-backnot-hardware-back-button

        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
