package ca.ualberta.c301w19t14.onebook;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;




public class BookTest {
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
    public Borrower steve =  new Borrower(steve_uid, steve_name, steve_email);
    public Borrower bob = new Borrower(bob_uid, bob_name, bob_email);
    public Location edmonton = new Location(edmonton_name, edmonton_lat, edmonton_long);
    public Location calgary = new Location(calgary_name, calgary_lat, calgary_long);

    public Book book = new Book(1897213, title, author,category,
            steve, bob, edmonton, status);


    @Test 
    public void test_Uid()
    {
        Assert.assertEquals(steve_uid, book.getOwner().getUid());
        Assert.assertEquals(bob_uid, book.getBorrower().getUid());
    }

    @Test
    public void testBookDetails(){
        Assert.assertEquals(isbn, book.getIsbn());
        Assert.assertEquals(title, book.getTitle());
        Assert.assertEquals(author, book.getAuthor());
        Assert.assertEquals(category, book.getCategory());
        Assert.assertEquals(status, book.getStatus());
    }

    @Test
    public void testEmail(){
        Assert.assertEquals(steve_email, book.getOwner().getEmail());
        Assert.assertEquals(bob_email, book.getBorrower().getEmail());
    }

    @Test
    public void testName(){
        Assert.assertEquals(steve_name, book.getOwner().getName());
        Assert.assertEquals(bob_name, book.getBorrower().getName());
    }

    @Test
    public void testLocation(){
        Assert.assertEquals(edmonton_name, book.getLocation().getName());
        Assert.assertEquals("message",edmonton_lat, book.getLocation().getLat(), 0.01);
        Assert.assertEquals("message",edmonton_long, book.getLocation().getLng(), 0.01);
    }

}
