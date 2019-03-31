package ca.ualberta.c301w19t14.onebook.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.models.Location;
import ca.ualberta.c301w19t14.onebook.models.Notification;
import ca.ualberta.c301w19t14.onebook.R;

/**
 * This class will set/view the pickup location on a Google Maps instance.
 * @author CMPUT301 Team14: Dimitri T
 * @version 1.0
 * @see ca.ualberta.c301w19t14.onebook.adapters.RequestsAdapter.RequestsViewHolder
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Book book;

    /**
     * Initializes the view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String book_id = getIntent().getExtras().getString("book_id");
        book = Globals.getInstance().books.getData().child(book_id).getValue(Book.class);
    }

    /**
     * Options for when the map has been displayed
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(book.getAcceptedRequest().getLocation() != null) {
            Location loc = book.getAcceptedRequest().getLocation();
            LatLng latLng = new LatLng(loc.getLat(), loc.getLng());

            AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
            alertDialog.setTitle("Pickup Location");
            if(book.getOwner().getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                alertDialog.setMessage("The pickup location is currently set to: " + loc.getAddress() + ". You can change the location by selecting a new point on the map.");
            } else {
                alertDialog.setMessage("The pickup location is currently set to: " + loc.getAddress() + ". If you would prefer a different location, please contact the owner using our messaging feature.");
            }
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

            mMap.addMarker(new MarkerOptions().position(latLng).title("Pick Up Location: " + loc.getAddress()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        if(book.getOwner().getUid().equals(Globals.getInstance().user.getUid())) {

            if(book.getAcceptedRequest().getLocation() == null) {
                AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                alertDialog.setTitle("Select a Pickup Location");
                alertDialog.setMessage("Now that you have accepted a request, you must select a pickup location for the borrower to pickup the book. Don't worry, you can change this location later. The borrower will always be notified.");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(final LatLng point) {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                    final Marker marker = mMap.addMarker(new MarkerOptions().position(point).title("New Pick Up Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(point));

                    try {
                        addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                    } catch (IOException e) {
                        Toast.makeText(MapsActivity.this, "Invalid location, please try again.", Toast.LENGTH_SHORT).show();
                        marker.remove();
                        return;
                    }

                    final String address = addresses.get(0).getAddressLine(0);

                    AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                    alertDialog.setTitle("Set New Location");
                    alertDialog.setMessage(address);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // update request location
                                    book.getAcceptedRequest().setLocation(new Location(address, point.latitude, point.longitude));
                                    book.update();
                                    Notification notification = new Notification("Pickup Location Set", "The owner of " + book.getTitle() + " set pickup at " + address, book.getAcceptedRequest().getUser());
                                    notification.save();
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    marker.remove();
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
        } else if(book.getAcceptedRequest().getLocation() == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
            alertDialog.setTitle("No Location Set");
            alertDialog.setMessage("The owner has yet to set a pickup location. Don't worry, you'll receive a notification when they do.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
