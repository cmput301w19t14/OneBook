package ca.ualberta.c301w19t14.onebook;

public class Owner extends User {
    
    Owner(String username, String password, String email, long phone, int userID) {

        super(username, password, email, phone, userID);

    } 

    public boolean lendBook(Book book, int userID) {
        //code to be added
        return true;
    }
}
