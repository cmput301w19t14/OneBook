package com.example.onebook;

public class Borrower extends User {

    Borrower(String username, String password, String email, long phone, long userID){
            super(username, password, email, phone, userID);
    }

    Borrower(String username, String password, String email){
        super(username, password, email);
    }

    public boolean requestBook(Book book, long userID) {

        if (book.getStatus().equals("available") && book.getOwner().getUserID() == userID) {
            return true;
        }
        else
            return false;
    }

    public boolean returnBook(Book book, long userID) {

        //code to be added
        return true;

    }
}
