package ca.ualberta.c301w19t14.onebook;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.models.Chat;
import ca.ualberta.c301w19t14.onebook.models.Location;
import ca.ualberta.c301w19t14.onebook.models.Request;
import ca.ualberta.c301w19t14.onebook.models.User;

public class ChatTest {

    private String sender = "Bill Murray";
    private String receiver = "Ryan Renolds";
    private String message = "Hey you old son of a gun, how the hell are you?";

    public Chat chat = new Chat(sender, receiver, message);

    @Test
    public void testGetSender(){
        Assert.assertEquals(sender, chat.getSender());
    }

    @Test
    public void testGetReceiver(){
        Assert.assertEquals(receiver, chat.getReceiver());
    }

    @Test
    public void testGetMessage(){
        Assert.assertEquals(message, chat.getMessage());
    }


}
