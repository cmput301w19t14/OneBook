package com.example.onebook;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;




public class BookTest {
    public ArrayList<Request> req;
    public Owner owny= new Owner("name","passwoed","email",123L,312L);
    public Borrower borrow =  new Borrower("name","passwoed","email",123L,312L);
    public Location place = new Location();




    @Test 
    public void test_book()
    {

        Book book = new Book(1,"Narnia","narnia guy","narnia book"
                ,req,owny,borrow, place, "available");
        boolean check_isbn;
        check_isbn = book.createViaISBNPhoto();
        Assert.assertTrue(check_isbn);
    }

}
