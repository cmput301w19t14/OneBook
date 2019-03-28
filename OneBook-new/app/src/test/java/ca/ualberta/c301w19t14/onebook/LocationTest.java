package ca.ualberta.c301w19t14.onebook;
import org.junit.Assert;
import org.junit.Test;

import ca.ualberta.c301w19t14.onebook.models.Location;


public class LocationTest {

    private Location location;

    public LocationTest() {
        location = new Location();
    }

    @Test
    public void test_location_latlng()
    {
        Location loc = new Location("test", 19.123213213, 12.123213213);

        Assert.assertEquals("test", loc.getName());
        Assert.assertEquals("message", 19.123213213,
                loc.getLat(), 0.01f);
        Assert.assertEquals("message", 12.123213213,
                loc.getLng(), 0.01f);
    }

    @Test
    public void test_location_full()
    {
        Location loc = new Location("test", "1525 99 Street", "Edmonton",
                "Alberta", "Canada");

        Assert.assertEquals("test", loc.getName());
        Assert.assertEquals("1525 99 Street", loc.getStreetAddress());
        Assert.assertEquals("Edmonton", loc.getCity());
        Assert.assertEquals("Alberta", loc.getState());
        Assert.assertEquals("Canada", loc.getCountry());
    }

    @Test
    public void test_set_name()
    {
        location.setName("Jandaile");
        Assert.assertEquals("Jandaile", location.getName());
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
        location.setStreetAddress("Jandaile");
        Assert.assertEquals("Jandaile", location.getStreetAddress());
    }

    @Test
    public void test_city()
    {
        location.setCity("Edmonton");
        Assert.assertEquals("Edmonton", location.getCity());
    }

    @Test
    public void test_state()
    {
        location.setState("Alberta");
        Assert.assertEquals("Alberta", location.getState());
    }

    @Test
    public void test_country()
    {
        location.setCountry("Canada");
        Assert.assertEquals("Canada", location.getCountry());
    }
}
