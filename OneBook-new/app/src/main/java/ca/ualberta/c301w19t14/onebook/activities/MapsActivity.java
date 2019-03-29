package ca.ualberta.c301w19t14.onebook.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.models.Location;
import ca.ualberta.c301w19t14.onebook.models.Notification;
import ca.ualberta.c301w19t14.onebook.R;

/**
 * Set/view the pickup location on a Google Maps instance.
 *
 * @author Dimitri Trofimuk
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String book_id = getIntent().getExtras().getString("book_id");
        book = Globals.getInstance().books.getData().child(book_id).getValue(Book.class);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(book.getAcceptedRequest().getLocation() != null) {
            Location loc = book.getAcceptedRequest().getLocation();
            LatLng latLng = new LatLng(loc.getLat(), loc.getLng());

            mMap.addMarker(new MarkerOptions().position(latLng).title("Pick Up Location :" + loc.getAddress()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            Toast.makeText(MapsActivity.this, "Pick Up Location :" + loc.getAddress(), Toast.LENGTH_SHORT).show();
        }

        if(book.getOwner().getUid().equals(Globals.getInstance().user.getUid())) {
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(final LatLng point) {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                    mMap.addMarker(new MarkerOptions().position(point).title("New Pick Up Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(point));

                    try {
                        addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                    } catch (IOException e) {
                        Toast.makeText(MapsActivity.this, "Invalid location, please try again.", Toast.LENGTH_SHORT).show();
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
                                    Notification notification = new Notification("Pickup Location Set", "The owner of " + book.getTitle() + " set pickup at " + address, book.getAcceptedRequest().getUser());
                                    notification.save();

                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
        }

    }
}
