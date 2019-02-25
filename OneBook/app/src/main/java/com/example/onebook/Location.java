package com.example.onebook;

public class Location {

    private double lat;
    private double lng;
    private String streetAddress;
    private String city;
    private String state;
    private String country;
    private String name;

    public Location() {

    }

    public Location(String name, double lat, double lng)
    {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public Location(String name, String streetAddress, String city, String state, String country)
    {
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return this.name;
    }

    public double getLat() {
        return this.lat;
    }

    public double getLng() {
        return this.lng;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getCountry() {
        return this.country;
    }
}
