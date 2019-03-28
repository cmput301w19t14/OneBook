package ca.ualberta.c301w19t14.onebook;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.models.Location;
import ca.ualberta.c301w19t14.onebook.models.Request;
import ca.ualberta.c301w19t14.onebook.models.User;

public class WaitlistTest {

    public String steve_uid = "13w74X57b";
    public String bob_uid = "43h82Y12a";
    public String steve_name = "Steve Walloc";
    public String bob_name = "Bob Marshall";
    public String steve_email = "steveW@gmail.com";
    public String bob_email = "bobM@gmail.com";

    public String edmonton_name = "Edmonton";
    public double edmonton_lat = 97.3f;
    public double edmonton_long = 31.8f;

    public String calgary_name = "Calgary";
    public double calgary_lat = 118.2f;
    public double calgary_long = 207.5f;

    //Book info
    public long isbn = 1897213L;
    public String title = "Narnia";
    public String author = "Narnia Guy";
    public String category = "Fantasy";
    public String status = "Requested";


    public ArrayList<Request> req;
    public User steve =  new User(steve_uid, steve_name, steve_email);
    public User bob = new User(bob_uid, bob_name, bob_email);
    public Location edmonton = new Location(edmonton_name, edmonton_lat, edmonton_long);
    public Location calgary = new Location(calgary_name, calgary_lat, calgary_long);

    public Book book = new Book(1897213, title, author,category,
            steve, bob, edmonton, status);

    public Waitlist waitlist = new Waitlist(book);

    @Test
    public void test_queue()
    {
        waitlist.addtoQueue(steve);
        Assert.assertEquals(steve_name, waitlist.seeNextInQueue().getName());
    }
    @Test
    public void test_queue_pop()
    {
        waitlist.addtoQueue(steve);
        waitlist.addtoQueue(bob);
        Assert.assertEquals(bob_name, waitlist.nextinqueue().getName());
        Assert.assertEquals(steve_name, waitlist.nextinqueue().getName());
    }

    @Test
    public void emptyQueue(){
        Assert.assertNull(waitlist.nextinqueue());
    }
}
