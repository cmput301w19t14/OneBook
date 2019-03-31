package ca.ualberta.c301w19t14.onebook.models;

import android.support.annotation.NonNull;

import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

/**
 * This class abstracts the Request data type.
 * @author CMPUT301 Team14: Natalie H, Dimitri T
 * @see ca.ualberta.c301w19t14.onebook.adapters.RequestsAdapter
 * @version 1.0
 */
public class Request {

    final public static String ACCEPTED = "Accepted";
    final public static String PENDING = "Pending";
    final public static String BORROWING = "Borrowing";
    final public static String PENDING_BORROWER_SCAN = "Pending Borrower Scan";
    final public static String PENDING_OWNER_SCAN = "Pending Owner Scan";

    private String id;
    private User user;
    private Book book;

    @NonNull
    private String status = "Pending";
    private Location location;

    /**
     * Firebase constructor.
     */
    public Request() {
    }

    /**
     * Request constructor.
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
     * Abstracts the request a book process.
     * @param user User object requesting the book
     * @param book Book being requested
     *
     */
    public static void requestBook(User user, Book book) {
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
                Notification borrower = new Notification("Book Requested", "You're first in line to receive " + book.getTitle(), user, Notification.BOOK);
                Notification owner = new Notification("New Request on Book", user.getName() + " has requested " + book.getTitle(), request, book.getOwner(), Notification.BOOK);

                // send notifications
                borrower.save();
                owner.save();

            } else {
                // add to wait list
                hMap = book.getRequest();

                // create notifications
                Notification borrower = new Notification("New Request on Book", "You've been added to the waitlist for " + book.getTitle(), user, Notification.BOOK);

                // send notifications
                borrower.save();
            }

            hMap.put(ts, request);
            book.setRequest(hMap);
            book.update();
        }
    }


    /**
     * Accepts the request, sends notifications, and updates status
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
        Notification accepted = new Notification("Request Accepted", book.getOwner().getName() + " has accepted your request on " + book.getTitle(), this.getUser(), Notification.BOOK);
        Notification borrowerMeetup = new Notification("Meet up Required", "You need to meet " + book.getOwner().getName() + " to pick up " + book.getTitle(), this.getUser(), Notification.COMPASS);
        Notification ownerMeetup = new Notification("Meet up Required", "You need to meet " + this.getUser().getName()+ " to give them " + book.getTitle(), book.getOwner(), Notification.COMPASS);

        // send notifications
        accepted.save();
        ownerMeetup.save();
        borrowerMeetup.save();
    }

    /**
     * Change the status.
     */
    public void commitNewStatus(String status) {
        Book book = this.getBook();

        // update the status
        FirebaseDatabase.getInstance()
                .getReference("Books")
                .child(book.getId())
                .child("request")
                .child(this.getId())
                .child("status")
                .setValue(status);
    }

    /**
     * Rejects the request, deletes request, and sends notifications
     */
    public void reject() {
        Book book = this.getBook();

        // delete the request
        FirebaseDatabase.getInstance().getReference("Books").child(book.getId()).child("request").child(this.getId()).removeValue();

        // create notifications
        Notification rejected = new Notification("Request Rejected", book.getOwner().getName() + " has rejected your request on " + book.getTitle(), this.getUser(), Notification.BOOK);

        // send notifications
        rejected.save();
    }

    /**
     * Deletes the request.
     */
    public void delete() {
        Book book = this.getBook();

        // delete the request
        FirebaseDatabase.getInstance().getReference("Books").child(book.getId()).child("request").child(this.getId()).removeValue();
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
    @NonNull
    public String getStatus() {

        return status;
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

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
