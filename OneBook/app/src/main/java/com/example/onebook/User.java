package com.example.onebook;

public abstract class User {

    private String username;
    private String password;
    private String email;
    private int phone;
    private int userID;

    public boolean Login( String username, String password) {
        boolean is_success = false;

        return is_success;
    }

    protected boolean changeUsername(String username) {
        boolean is_success = false;

        return is_success;
    }

    protected boolean changePassword(String password) {
        boolean is_success = false;

        return is_success;
    }

    public boolean editContactInfo( String email, int phone) {
        boolean is_success = false;

        return is_success;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}

