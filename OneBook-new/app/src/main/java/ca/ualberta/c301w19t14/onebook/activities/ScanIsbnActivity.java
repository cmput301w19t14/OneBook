package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.R;

public class ScanIsbnActivity extends AppCompatActivity {
    TextView barcodeResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan_isbn);

        barcodeResult = (EditText) findViewById(R.id.enterIsbnManual);
        barcodeResult.setHint("Enter ISBN");

        Button findBookButton = findViewById(R.id.findBookButton);
        findBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcode = barcodeResult.getText().toString();

                // check if ISBN is entered
                if (barcode.matches("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You did not enter ISBN", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    long isbn = Long.parseLong(barcode);
                    DataSnapshot book = Globals.getInstance().books.getData();
                    for (DataSnapshot i : book.getChildren()) {
                        Book item = i.getValue(Book.class);
                        if(item.getIsbn() == isbn) {
                            if(item.getOwner().getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                // user is owner: we go to view book activity
                                Intent intent = new Intent(ScanIsbnActivity.this, ViewBookActivity.class);
                                final Bundle bundle = new Bundle();
                                String id = item.getId();
                                bundle.putString("id", id);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                        else {
                            // user is not owner: go to edit book where everything is empty, but ISBN is autofilled
                            Intent intent = new Intent(ScanIsbnActivity.this, AddActivity.class);
                            intent.putExtra("ISBN", barcode);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        Button scanButton =  findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanIsbnActivity.this, CameraActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    barcodeResult.setText(barcode.displayValue);
                }
                else {
                    barcodeResult.setHint("No barcode found. Please enter manually");
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

