package com.example.onebook;

public class Owner extends User {
    
    Owner(String username, String password, String email, long phone, int userID) {
        super(username, password, email, phone, userID);
    }
    Owner(String username, String password, String email){
        super(username, password, email);
    }

    public boolean lendBook(Book book, int userID) {
        //code to be added
        return true;
    }
}
