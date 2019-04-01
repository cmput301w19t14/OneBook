package ca.ualberta.c301w19t14.onebook.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import ca.ualberta.c301w19t14.onebook.models.Request;

/**
 * This class implements a scanner for ISBN codes. Uses phone's camera to create the scanner.
 *
 * @author CMPUT301 Team14: Ana, Dimitri
 * @version 1.0
 */
public class ScanIsbnActivity extends AppCompatActivity {
    TextView barcodeResult;

    /**
     * Initializes the view.
     * @param savedInstanceState
     */
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
                } else {
                    long isbn = Long.parseLong(barcode);
                    DataSnapshot book = Globals.getInstance().books.getData();
                    Boolean exists = false;
                    for (DataSnapshot i : book.getChildren()) {
                        final Book item = i.getValue(Book.class);
                        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        if (item.getIsbn() == isbn) {
                            exists = true;

                            if (item.acceptedRequest() != null && (item.getOwner().getUid().equals(userId) || item.acceptedRequest().getUser().getUid().equals(userId))) {
                                // an accepted request exists, and the current user is either
                                // the owner or borrower-to-be.
                                if (item.getOwner().getUid().equals(userId)) {
                                    // the user is the owner
                                    switch (item.acceptedRequest().getStatus()) {
                                        case Request.ACCEPTED:
                                            showOwnerInitiate(v, item);
                                            Snackbar.make(findViewById(R.id.scanIsbn), "Handover process initiated. Waiting on borrower scan. Scan again to view book details.", Snackbar.LENGTH_LONG).show();
                                            break;
                                        case Request.PENDING_OWNER_SCAN:
                                            // confirm returned
                                            item.finishReturnHandover();
                                            // TODO: WAITLIST STUFF HERE
                                            // notify success
                                            Snackbar.make(findViewById(R.id.scanIsbn), "Return process complete. The book has been returned. Scan again to view book details.", Snackbar.LENGTH_LONG).show();
                                            break;
                                        default:
                                            // go to view book
                                            goToViewBook(item);
                                            break;
                                    }
                                } else {
                                    // the user is the borrower
                                    switch (item.acceptedRequest().getStatus()) {
                                        case Request.PENDING_BORROWER_SCAN:
                                            item.finishBorrowHandover();
                                            Snackbar.make(findViewById(R.id.scanIsbn), "Handover process complete. You are now the book borrower. Scan again to view book details.", Snackbar.LENGTH_LONG).show();
                                            break;
                                        case Request.BORROWING:
                                            showBorrowerInitiate(v, item);
                                            Snackbar.make(findViewById(R.id.scanIsbn), "Return process initiated. Waiting on owner scan. Scan again to view book details.", Snackbar.LENGTH_LONG).show();
                                            break;
                                        default:
                                            // go to view book
                                            goToViewBook(item);
                                            break;
                                    }
                                }

                            } else {
                                // to view book page
                                goToViewBook(item);
                            }
                        }
                    }
                    if(!exists) {
                        Intent intent = new Intent(ScanIsbnActivity.this, AddActivity.class);
                        final Bundle bundle = new Bundle();
                        bundle.putString("isbn", Long.toString(isbn));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
            });

        Button scanButton =  findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ScanIsbnActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ScanIsbnActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 1);
                }
                else {
                    Intent intent = new Intent(ScanIsbnActivity.this, CameraActivity.class);
                    startActivityForResult(intent, 0);

                }
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

                    // need to click button
                    findViewById(R.id.findBookButton).performClick();
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

    /**
     * This method initiates the handover process from a owner from a book request.
     * @param v: from a findBookButton click. Brought up for a request
     * @param item: the book from the owner that is being requested by a borrower
     */
    private void showOwnerInitiate(View v, final Book item) {
        AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
        alertDialog.setTitle("Choose an option:");
        alertDialog.setMessage("There is an accepted request on this book. You can either initiate the handover process, or view book details.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "HAND OVER",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        item.doBorrowHandover();
                        Snackbar.make(findViewById(R.id.scanIsbn), "Handover process initiated. Waiting on borrower scan. Scan again to view book details.", Snackbar.LENGTH_LONG).show();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "VIEW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        goToViewBook(item);
                    }
                });
        alertDialog.show();
    }

    /**
     * This method initiates the return process from a borrower to the owner of the book.
     * @param v: from a findBookButton click. Brought up for a request
     * @param item: the book from the owner that is being requested by a borrower
     */
    private void showBorrowerInitiate(View v, final Book item) {
        AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
        alertDialog.setTitle("Choose an option:");
        alertDialog.setMessage("You are currently borrowing this book. You can either initiate the return process, or view book details.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "RETURN",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        item.doReturnHandover();
                        Snackbar.make(findViewById(R.id.scanIsbn), "Return process initiated. Waiting on owner scan. Scan again to view book details.", Snackbar.LENGTH_LONG).show();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "VIEW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        goToViewBook(item);
                    }
                });
        alertDialog.show();
    }

    /**
     * This method moves to ViewBookActivity to see the book's descriptors.
     * @param item: the book object to be looked at.
     */
    private void goToViewBook(Book item) {
        Intent intent = new Intent(ScanIsbnActivity.this, ViewBookActivity.class);
        final Bundle bundle = new Bundle();
        String id = item.getId();
        bundle.putString("id", id);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

