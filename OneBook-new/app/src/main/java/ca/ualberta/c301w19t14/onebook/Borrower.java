package ca.ualberta.c301w19t14.onebook;

public class Borrower extends User {

    Borrower (){

    }

    Borrower (String uid, String name, String email){
        super(uid, name, email);
    }

    public boolean requestBook(Book book, long userID) {   

        //if (book.getStatus().equals("available") && book.getOwner().getUserID() == userID) {
        //    return true;
        //}
        //else
            return false;
    }

    public boolean returnBook(Book book, long userID) {

        //code to be added
        return true;

    }
}
