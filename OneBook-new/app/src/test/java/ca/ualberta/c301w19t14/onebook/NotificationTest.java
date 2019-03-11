package ca.ualberta.c301w19t14.onebook;

import org.junit.Test;
import org.junit.Assert;

import ca.ualberta.c301w19t14.onebook.Notification;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NotificationTest {

    private Notification notificationTest = new Notification();

    @Test
    public void checkCreateNotification(){
        Assert.assertEquals(Boolean.TRUE, notificationTest.createNotification(3, "100","200", "This is my message"));
    }

    @Test
    public void checkDeleteNotification(){
        Assert.assertEquals(Boolean.TRUE, notificationTest.deleteNotification(3, "100","200", "This is my message"));
    }

    @Test
    public void checkSendNotification(){
        Assert.assertEquals(Boolean.TRUE, notificationTest.sendNotification(3, "100","200", "This is my message"));
    }

    @Test
    public void checkSetRead() {
        notificationTest.setRead(Boolean.TRUE);
        Assert.assertEquals(Boolean.TRUE,notificationTest.getRead());
    }

    @Test
    public void checkSetNotification() {
        notificationTest.setNotification("this is my message");
        Assert.assertEquals("this is my message", notificationTest.getNotification());
    }

    @Test
    public void checkSetSenderID() {
        String i = "257";
        notificationTest.setSenderID(i);
        Assert.assertEquals(i, notificationTest.getSenderID());
    }

    @Test
    public void checkSetRecipientID() {
        String j = "377";
        notificationTest.setRecipientID(j);
        Assert.assertEquals(j, notificationTest.getRecipientID());
    }

}



