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
public class MessageTest {

    private Message message;

    @Test
    public void testSetUser(){
        message.setUser("Clark");
        Assert.assertEquals("Clark", message.getUser());
    }

    @Test
    public void testSetMessage(){
        message.setMessage("Hello there!");
        Assert.assertEquals("Hello there!", message.getMessage());
    }

    @Test
    public void testSetDate(){
        message.setDate("13/08/2019");
        Assert.assertEquals("13/08/2019", message.getDate());
    }
}