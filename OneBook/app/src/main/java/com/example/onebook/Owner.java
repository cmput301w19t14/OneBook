package com.example.onebook;

public class Owner extends User {

    Owner(String username, String password, String email, long phone, int userID) {

        super(username, password, email, phone, userID);

    }

    public void lendBook(int ISBN, int userID) {

        return;
    }
}
