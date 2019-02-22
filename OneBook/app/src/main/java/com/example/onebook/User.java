package com.example.onebook;

public abstract class User {

    private String username;
    private String password;
    private String email;
    private long phone;
    private int userID;

    User(String username, String password, String email, long phone, int userID){
        setEmail(email);
        changeUsername(username);
        changePassword(password);
        setPhone(phone);
        setUserID(userID);
    }

    public boolean Login( String username, String password) {
        boolean is_success = true;

        return is_success;
    }

    protected boolean changeUsername(String username) {
        boolean is_success = true;

        return is_success;
    }

    protected boolean changePassword(String password) {
        boolean is_success = true;

        return is_success;
    }

    public boolean editContactInfo( String email, long phone) {
        boolean is_success = true;

        is_success = setEmail(email);
        is_success = setPhone(phone);

        return is_success;
    }

    public boolean setEmail(String email){

        boolean is_success = true;

        this.email = email;

        return is_success;
    }

    public boolean setPhone(long phone){

        boolean is_success = true;

        this.phone = phone;

        return is_success;
    }

    public boolean setUserID(int userID){
        boolean is_success = true;

        this.userID = userID;

        return is_success;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail() {
        return email;
    }

    public long getPhone() {
        return phone;
    }

    public int getUserID() {
        return userID;
    }
}
