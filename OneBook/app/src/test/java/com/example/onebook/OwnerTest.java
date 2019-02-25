package com.example.onebook;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class OwnerTest {

    public ArrayList<Request> req;
    public Borrower borrow;
    public Location place;

    private Owner owner = new Owner();
    private Book book = new Book(1,"Narnia","narnia guy","narnia book"
            ,req,owner,borrow, place, "available");
    @Test
    public void checkLendBook() {

        assertTrue(owner.lendBook(book, 20));


    }
    
}