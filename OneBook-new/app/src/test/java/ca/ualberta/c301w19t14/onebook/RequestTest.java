package ca.ualberta.c301w19t14.onebook;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.models.Location;
import ca.ualberta.c301w19t14.onebook.models.Notification;
import ca.ualberta.c301w19t14.onebook.models.Request;
import ca.ualberta.c301w19t14.onebook.models.User;


public class RequestTest {

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
    private String request_id = "48wH221U";
    public String request_id2 = "dfa52GH775";
    public String title = "Narnia";
    public String author = "Narnia Guy";
    public String description = "Fantasy book about lions";
    public String status = "Requested";

    private String date = "21/08/2019";

    //request data
    private String requesttitle = "Title of the Request";
    private String requestcontent = "this is the content of the Request";


    public ArrayList<Request> req;
    public User steve =  new User(steve_uid, steve_name, steve_email);
    public User bob = new User(bob_uid, bob_name, bob_email);
    public Location edmonton = new Location(edmonton_name, edmonton_lat, edmonton_long);
    public Location calgary = new Location(calgary_name, calgary_lat, calgary_long);

    public Book book = new Book(1897213, title, author,description,
            steve);

    public Request request = new Request(bob, book, request_id);
    public Notification notification = new Notification(requesttitle, requestcontent,request,
            steve);


    @Test
    public void testSetOwner() {
        request.setUser(steve);
        Assert.assertNotEquals(bob_email, request.getUser().getEmail());
        Assert.assertEquals(steve_email, request.getUser().getEmail());
    }

    @Test
    public void testSetLocation() {
        request.setLocation(edmonton);
        Assert.assertEquals(edmonton_name, request.getLocation().getAddress());
    }

    @Test
    public void testSetID() {
        request.setId(request_id2);
        assertNotEquals(request_id, request.getId());
        assertEquals(request_id2, request.getId());
    }
}
