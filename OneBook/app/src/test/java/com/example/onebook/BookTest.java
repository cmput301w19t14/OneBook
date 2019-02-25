package com.example.onebook;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class BookTest {
    public ArrayList<Request> req;
    public Owner owny;
    public Borrower borrow;
    public Location place;



    @Test 
    public void test_book()
    {

        Book book = new Book(1,"Narnia","narnia guy","narnia book"
                ,req,owny,borrow, place, "available");
        boolean check_isbn;
        check_isbn = book.createViaISBNPhoto();
        assertTrue(check_isbn);
    }
}
