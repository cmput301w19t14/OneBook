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
<<<<<<< HEAD
    private Location location = new Location("test", "Paris", "1124 Street", "France", "T6G 2T7");
=======
    private Location location = new Location(
            "mark hamil", 12.344, 14.789 );
>>>>>>> bc996ef23c530e20eff1c28c402583118819cd80
    private Request request = new Request(owner, borrower, book);

    @Test
    public void testSendNotification() {
        assertTrue(request.sendNotification());
    }

    @Test
    public void testSetLocation() {
        request.setLocation(location);
        assertEquals(location, request.getLocation());
    }

    @Test
    public void testSetOwner() {
        Owner owner1 = new Owner("mary", "hunter2", "john2@gmail.com",
                7804054451L, 110566);
        request.setOwner(owner1);
        assertEquals(owner1, request.getOwner());
    }

    @Test
    public void testSetBorrower() {
        Borrower borrower1 = new Borrower("tom", "sam12", "sam@gmail.com", 121544789, 113411);
<<<<<<< HEAD
        request.setBorrower(borrower1);
        assertEquals(borrower1, request.getBorrower());
=======
        assertEquals(request.getBorrower(), request.setBorrower(borrower1));
>>>>>>> bc996ef23c530e20eff1c28c402583118819cd80
    }

    @Test
    public void testSetBook() {
        Book book1 = new Book(123,"Another Narnia","narnia guy","narnia book",
                req,owner,borrow, place, "available");
        request.setBook(book1);
        assertEquals(book1, request.getBook());
    }
}
