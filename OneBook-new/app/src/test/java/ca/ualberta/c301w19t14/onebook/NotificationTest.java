package ca.ualberta.c301w19t14.onebook;

import org.junit.Test;
import org.junit.Assert;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.models.Notification;
import ca.ualberta.c301w19t14.onebook.models.Request;
import ca.ualberta.c301w19t14.onebook.models.User;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NotificationTest {

    private String title = "Title";
    private String content = "this is content";

    private String username = "John Stuart";
    private String useremail = "johns@ualberta.ca";
    private String useruid = "w65E6Y72S";
    private User owner = new User(useruid, username, useremail);

    private String borrowname = "Meek Mill";
    private String borrowemail = "millm@ualberta.ca";
    private String borrowuid = "a47NM7T12A";
    private User borrower = new User(borrowuid, borrowname, borrowemail);

    private String booktitle = "Harry Potter";
    private String author = "J.K. Rowling";
    private String description = "About wizards and magic";
    private long isbn = 27892231L;
    private Book book = new Book(isbn, booktitle, author, description, owner);

    private Request request = new Request(borrower, book);
    private Notification notificationTest = new Notification(title, content, request, owner);

    @Test
    public void checkGetTitle(){
        Assert.assertEquals(title, notificationTest.getTitle());
    }

    @Test
    public void checkGetContent(){
        Assert.assertEquals(content, notificationTest.getContent());
    }

    @Test
    public void checkGetRequestUserDetails(){
        Assert.assertEquals(borrowemail, request.getUser().getEmail());
        Assert.assertEquals(borrowname, request.getUser().getName());
        Assert.assertEquals(borrowuid, request.getUser().getUid());
    }

    @Test
    public void checkGetRequestBookDetails() {
        Assert.assertEquals(isbn, request.getBook().getIsbn());
        Assert.assertEquals(booktitle, request.getBook().getTitle());
        Assert.assertEquals(username, request.getBook().getOwner().getName());
    }

    @Test
    public void checkGetNotificationRequestDetails() {
        Assert.assertEquals(borrowname, notificationTest.getRequest().getUser().getName());
        Assert.assertEquals(booktitle, notificationTest.getRequest().getBook().getTitle());
        Assert.assertEquals(borrowuid, notificationTest.getRequest().getUser().getUid());
    }

    @Test
    public void checkGetNotificationUserDetails() {
        Assert.assertEquals(username, notificationTest.getUser().getName());
        Assert.assertEquals(useremail, notificationTest.getUser().getEmail());
        Assert.assertEquals(useruid, notificationTest.getUser().getUid());
    }

}



