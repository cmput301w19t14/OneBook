package com.example.onebook;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class WaitlistTest {
    public ArrayList<Request> req;
    public Owner owny;
    public Borrower borrow;
    public Location place;
    public User use;
    private ArrayList<User> users = new ArrayList<User>();
    private Book b = new Book(1,"Narnia","narnia guy","narnia book"
            ,req,owny,borrow, place, "available");
    public Waitlist waitlist = new Waitlist(users,b);

    @Test
    public void test_queue()
    {
        Assert.assertTrue(waitlist.addtoQueue(use));

    }
    @Test
    public void test_queue_pop()
    {
        Assert.assertEquals(use,waitlist.nextinqueue());

    }
    @Test
    public void test_notify()
    {
        Assert.assertTrue(waitlist.notifyUsers(borrow,owny));

    }
}
