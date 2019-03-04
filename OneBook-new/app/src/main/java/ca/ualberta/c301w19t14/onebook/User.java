package ca.ualberta.c301w19t14.onebook;

public abstract class User {

    private String username;
    private String password;
    private String email;
    private long phone;
    private long userID;
    
    public User( String username, String password, String email, long phone, long userID){

	    this.username = username;
	    this.password = password;
	    this.email = email;
	    this.phone = phone;
	    this.userID = userID;

    }

    public User( String username, String password, String email){

        this.username = username;
        this.password = password;
        this.email = email;

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

    public void editContactInfo( String email, long phone) {
        boolean is_success = false;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserID(long userID){
        this.userID = userID;
    }

    public long getUserID() {
        return userID;
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

