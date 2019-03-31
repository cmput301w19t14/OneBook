package ca.ualberta.c301w19t14.onebook.models;

/**
 * This class is mainly used to abstract location data for book pickup.
 * @author CMPUT301 Team14: Dimitri T
 * @see ca.ualberta.c301w19t14.onebook.activities.MapsActivity
 * @version 1.0
 */
public class Location {

    private double lat;
    private double lng;
    private String address;

    /**
     * Empty constructor for Firebase.
     */
    public Location() {

    }

    /**
     * Create a new location record with address and LatLng.
     *
     * @param address human formatted address
     * @param lat latitude
     * @param lng longitude
     */
    public Location(String address, double lat, double lng)
    {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * Set the address.
     *
     * @param address human formatted address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Set the lat.
     *
     * @param lat latitude
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Set the lng.
     *
     * @param lng longitude
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * Get the address.
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get the lat.
     *
     * @return lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * Get the lng.
     *
     * @return lng
     */
    public double getLng() {
        return lng;
    }
}
