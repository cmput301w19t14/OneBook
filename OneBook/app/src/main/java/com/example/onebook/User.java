package com.example.onebook;

public abstract class User {

    private String username;
    private String password;
    private String email;
    private long phone;
    private int userID;
    
    public User( String username, String password, String email, long phone, int userID){

	    this.username = username;
	    this.password = password;
	    this.email = email;
	    this.phone = phone;
	    this.userID = userID;

    }

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

    public boolean editContactInfo( String email, long phone) {
        boolean is_success = false;

        return is_success;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public long getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}

