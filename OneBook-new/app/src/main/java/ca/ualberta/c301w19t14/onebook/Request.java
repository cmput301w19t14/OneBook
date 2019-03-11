package ca.ualberta.c301w19t14.onebook;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;
import java.util.Date;

public class Request {

    private String date;
    private Location location;
    private String owneremail;
    private String requesteremail;
    private long ISBN;
    private String status;

    public Request(String requesteremail, long ISBN, String owneremail){
        // https://stackoverflow.com/questions/8077530/android-get-current-timestamp
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        this.requesteremail = requesteremail;
        this.ISBN = ISBN;
        this.owneremail = owneremail;
        this.date = ts;
    }

    public Request(String requesteremail, long ISBN, String owneremail, String date){


        this.requesteremail = requesteremail;
        this.ISBN = ISBN;
        this.owneremail = owneremail;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public long getISBN() {
        return ISBN;
    }

    public String getOwneremail() {
        return owneremail;
    }

    public String getRequesteremail() {
        return requesteremail;
    }

    public String getStatus() {
        return status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    public void setOwneremail(String owneremail) {
        this.owneremail = owneremail;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setRequesteremail(String requesteremail) {
        this.requesteremail = requesteremail;
    }

    /*
    Request(User user, Book book, Location location, String date){
        this.user = user;
        this.book = book;
        this.location = location;
        this.date = date;
    }
    */


    /*
    public static void requestBook(User user, Book book) {
        // save to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Requests");
        myRef.child(book.getId()).push().setValue(new Request(user, book));

        // notify the user
    }
    */


    public void setLocation() {
        // TODO
    }
}
