package com.example.onebook;
import org.junit.Test;

import static org.junit.Assert.*;


public class BookTest {
    private Book book = new Book();
    @Test 
    public void test_book()
    {
        boolean check_isbn;
        check_isbn = book.createViaISBNPhoto();
        assertEquals(true, check_isbn);
    }
}
