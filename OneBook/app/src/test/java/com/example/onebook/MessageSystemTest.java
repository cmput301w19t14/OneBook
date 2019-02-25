package com.example.onebook;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MessageSystemTest {

    public MessagingSystem messagingSystem = new MessagingSystem();
    public Borrower receivingUser = new Borrower("Kyle", "yoloswag",
            "toohotty@ualberta.ca", 7806969L, 12234);
    public Borrower user = new Borrower("Mark", "123", "dmark@ualberta.ca",
            5877232235L, 12334);

    @Test
    public void testSetUser(){
        messagingSystem.setUser(user);
    }

    @Test
    public void checkSend(){
        Assert.assertTrue(messagingSystem.sendMessage(receivingUser, "Sup bro!"));
    }

    @Test
    public void checkGetMessages(){
        Assert.assertTrue(messagingSystem.checkNewMessages());
    }
}