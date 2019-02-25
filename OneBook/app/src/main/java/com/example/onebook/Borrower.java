package com.example.onebook;

public class Borrower extends User {

    public boolean requestBook(Book book, int userID) {

        if (book.getStatus().equals("available") && book.getOwner().getUserID() == userID) {
            return true;
        }
        else
            return false;
    }

    public boolean returnBook(Book book, int userID) {

        //code to be added
        return true;

    }
}
