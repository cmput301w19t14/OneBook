package ca.ualberta.c301w19t14.onebook;

/**Class for Location. This will be used in Part 5 when implementing the pick up locations
 *  @author CMPUT 301 Team 14*/
public class Location {

    private double lat;
    private double lng;
    private String streetAddress;
    private String city;
    private String state;
    private String country;
    private String name;

    /**
     *
     */
    public Location() {

    }

    /**
     *
     * @param name
     * @param lat
     * @param lng
     */
    public Location(String name, double lat, double lng)
    {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    /**
     *
     * @param name
     * @param streetAddress
     * @param city
     * @param state
     * @param country
     */
    public Location(String name, String streetAddress, String city, String state, String country)
    {
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    /**
     * setter for the location name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setter for the street address
     * @param streetAddress
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * setter for the location's latitude
     * @param lat
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * setter for the location's longitude
     * @param lng
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * setter for the location's city
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * setter for the location's province/state/territory
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * setter for the location's country
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * getter for the location's name
     * @return this.name
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter for the location's latitude
     * @return this.lat
     */
    public double getLat() {
        return this.lat;
    }

    /**
     * getter for the location's longitude
     * @return this.lng
     */
    public double getLng() {
        return this.lng;
    }

    /**
     * getter for the location's address
     * @return this.streetAdress
     */
    public String getStreetAddress() {
        return this.streetAddress;
    }

    /**
     * getter for the location's city
     * @return this.city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * getter for the location's state/province/territory
     * @return this.state
     */
    public String getState() {
        return this.state;
    }

    /**
     * getter for the location's country
     * @return this.country
     */
    public String getCountry() {
        return this.country;
    }
}
