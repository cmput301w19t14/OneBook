package com.example.onebook;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;


public class RequestTest {

    public ArrayList<Request> req;
    public Borrower borrow;
    public Location place;

    private Owner owner = new Owner("john", "hunter2", "john2@gmail.com",
            7804054451L, 110521);
    private Book book = new Book(1,"Narnia","narnia guy","narnia book",
            req,owner,borrow, place, "available");
    private Borrower borrower = new Borrower("sam", "sam12", "sam@gmail.com", 121544789, 11111);
    private Location location = new Location(12.11, 12.33, "Paris", "1124 Street", "France", "T6G 2T7");
    private Request request = new Request(owner, borrower, book);

    @Test
    public void testSendNotification() {
        assertTrue(request.sendNotification());
    }

    @Test
    public void testSetLocation() {

        assertEquals(request.getLocation(), request.setLocation(location));
    }

    @Test
    public void testSetOwner() {
        Owner owner1 = new Owner("mary", "hunter2", "john2@gmail.com",
                7804054451L, 110566);
        assertEquals(request.getOwner(), request.setOwner(owner1));
    }

    @Test
    public void testSetBorrower() {
        private Borrower borrower1 = new Borrower("tom", "sam12", "sam@gmail.com", 121544789, 113411);
        assertEquals(request.getBorrower(), request.setBorrower(borrower1));
    }

    @Test
    public void testSetBook() {
        Book book1 = new Book(123,"Another Narnia","narnia guy","narnia book",
                req,owner,borrow, place, "available");
        assertEquals(request.getBook(), request.setBook(book1));
    }
}
