package ca.ualberta.c301w19t14.onebook;

public class Owner extends User {
    
    Owner(String username, String password, String email, long phone, long userID) {

        super(username, password, email, phone, userID);

    } 

    public boolean lendBook(Book book, long userID) {
        //code to be added
        return true;
    }
}
