package com.example.onebook;

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
public class BorrowerTest {

    public ArrayList<Request> req;
    public Owner own;
    public Location place;

    private Borrower borrower = new Borrower("Claire", "orchard", "claire@hotmail.com",
            5872172245L, 112546L);
    private Book book = new Book(1,"Narnia","narnia guy","narnia book"
            ,req,own,borrower, place, "available");

    @Test
    public void checkRequestBook(){

            book.getOwner().setUserID(15);
            boolean testTrue = borrower.requestBook(book, 15);
            assertTrue(testTrue);

            book.getOwner().setUserID(14);
            boolean testFalse = borrower.requestBook(book, 15);
            assertFalse(testFalse);
    }

    @Test
    public void checkReturnBook() {
            assertTrue(borrower.returnBook(book, 15));
    }
}
