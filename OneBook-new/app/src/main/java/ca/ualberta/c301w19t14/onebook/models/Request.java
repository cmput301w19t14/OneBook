package ca.ualberta.c301w19t14.onebook.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import ca.ualberta.c301w19t14.onebook.Globals;

/**
 * Abstracts the Request data type.
 *
 * @author Natalie, Dimitri
 */
public class Request {

    final static String ACCEPTED = "Accepted";

    private String id;
    private Location location;
    private User user;
    private Book book;
    private String status;

    public Request() {
    }

    /**
     * @param user
     * @param book
     */
    public Request(User user, Book book, String requestID) {
        this.user = user;
        this.book = book;
        this.status = "Pending";
        this.id = requestID;
    }

    /**
     * @param user
     */
    public static void requestBook(User user, Book book, String book_id) {
        // https://stackoverflow.com/questions/8077530/android-get-current-timestamp
        if(!book.userHasRequest(user)) {
            HashMap<String, Request> hMap = new HashMap<>();

            Long tsLong = System.currentTimeMillis() / 1000;
            String ts = tsLong.toString();

            Book cpy = new Book(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getOwner());
            cpy.setId(book.getId());
            Request request = new Request(user, cpy, ts);

            if (book.getRequest() == null || (book.getRequest() != null && book.getRequest().isEmpty())) {
                // no requests on book

                // create notifications
                Notification borrower = new Notification("Book Requested", "You're first in line to receive " + book.getTitle(), user);
                Notification owner = new Notification("New Request on Book", user.getName() + " has requested " + book.getTitle(), request, book.getOwner());

                // send notifications
                borrower.save();
                owner.save();

            } else {
                // add to wait list
                hMap = book.getRequest();

                // create notifications
                Notification borrower = new Notification("New Request on Book", "You've been added to the waitlist for " + book.getTitle(), user);

                // send notifications
                borrower.save();
            }

            hMap.put(ts, request);
            book.setRequest(hMap);
            book.update();
        }
    }


    /**
     * Accepts the request.
     *  * sends notifications
     *  * updates status
     */
    public void accept() {
        Book book = this.getBook();

        // update the status
        FirebaseDatabase.getInstance()
                .getReference("Books")
                .child(book.getId())
                .child("request")
                .child(this.getId())
                .child("status")
                .setValue("Accepted");

        // create notifications
        Notification accepted = new Notification("Request Accepted", book.getOwner().getName() + " has accepted your request on " + book.getTitle(), this.getUser());
        Notification borrowerMeetup = new Notification("Meet up Required", "You need to meet " + book.getOwner().getName() + " to pick up " + book.getTitle(), this.getUser());;
        Notification ownerMeetup = new Notification("Meet up Required", "You need to meet " + this.getUser().getName()+ " to give them " + book.getTitle(), book.getOwner());

        // send notifications
        accepted.save();
        ownerMeetup.save();
        borrowerMeetup.save();
    }

    /**
     * Rejects the request.
     *  * deletes request
     *  * sends notifications
     */
    public void reject() {
        Book book = this.getBook();

        // delete the request
        FirebaseDatabase.getInstance().getReference("Books").child(book.getId()).child("request").child(this.getId()).removeValue();

        // create notifications
        Notification rejected = new Notification("Request Rejected", book.getOwner().getName() + " has rejected your request on " + book.getTitle(), this.getUser());

        // send notifications
        rejected.save();
    }

    /**
     *
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @return book
     */
    public Book getBook() {
        return book;
    }

    /**
     *
     * @return status
     */
    public String getStatus() {

        return status;
    }

    /**
     *
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @param book
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

}
