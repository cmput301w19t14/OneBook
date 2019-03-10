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
    private User user;
    private Book book;
    private String status;

    Request() {

    }

    Request(User user, Book book){
        // https://stackoverflow.com/questions/8077530/android-get-current-timestamp
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        this.user = user;
        this.book = book;
        this.date = ts;
    }

    Request(User user, Book book, Location location, String date){
        this.user = user;
        this.book = book;
        this.location = location;
        this.date = date;
    }

    public static void requestBook(User user, Book book) {
        // save to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Requests");
        myRef.child(book.getId()).push().setValue(new Request(user, book));

        // notify the user
    }

    public void setLocation() {
        // TODO
    }
}
