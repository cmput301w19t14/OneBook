package ca.ualberta.c301w19t14.onebook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ualberta.c301w19t14.onebook.models.Location;


public class LocationTest {

    private Location location;
    private String address;
    private double lat;
    private double lng;

    public LocationTest() {
        location = new Location();
    }


    @Before
    public void init(){
        address = "9232 Baker Street NW";
        lat = 167.22;
        lng = 58.75;
        location = new Location(address, lat, lng);
    }

    @Test
    public void test_location_lat()
    {
        Assert.assertEquals(lat, location.getLat(), 0.001);
    }

    @Test
    public void test_location_lng()
    {
        Assert.assertEquals(lng, location.getLng(), 0.001);
    }

    @Test
    public void test_Address()
    {
        Assert.assertEquals(address, location.getAddress());
    }

    @Test
    public void test_set_lat()
    {
        location.setLat(19.231234111);
        Assert.assertEquals("message", 19.231234111,
                location.getLat(), 0.01f);
    }

    @Test
    public void test_set_lng()
    {
        location.setLng(19.231234111);
        Assert.assertEquals("message", 19.231234111,
                location.getLng(), 0.01f);
    }

    @Test
    public void test_streetAddress()
    {
        location.setAddress("4133 Pegasus Drive SW");
        Assert.assertEquals("4133 Pegasus Drive SW", location.getAddress());
    }


}
