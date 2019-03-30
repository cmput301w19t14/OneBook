package ca.ualberta.c301w19t14.onebook.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.ualberta.c301w19t14.onebook.Globals;

/**This class implements the transaction of a user requesting a book and updates the database
 * @author CMPUT 301 Team 14*/
public class Request {

    final static String ACCEPTED = "Accepted";

    private String id;
    private String date;
    private Location location;
    private User user;
    private Book book;
    private String status;
    private String owneremail;
    private String requesteremail;
    private String time;
    private long ISBN;

    public Request() {
    }

    /**
     * @param user
     */
    public Request(User user, String requestID) {
        this.user = user;
        this.status = "Pending";
        this.id = requestID;
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
     * @param book
     * @param location
     * @param date
     * @param status
     */
    public Request(User user, Book book, Location location, String date, String status) {
        this.user = user;
        //this.book = book;
        this.location = location;
        this.date = date;
        this.status = status;
    }

    /**
     * Deletes a request.
     */
    public void delete() {
        //FirebaseDatabase.getInstance().getReference("Books").child(this.getId()).child("request").child(this.).removeValue();
    }


    /**
     * @param user
     */
    public static void requestBook(User user, Book full_book, String book_id) {
        // https://stackoverflow.com/questions/8077530/android-get-current-timestamp
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();

        Book book = new Book(full_book.getIsbn(), full_book.getTitle(), full_book.getAuthor(), full_book.getDescription(), full_book.getOwner(),0);

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database2.getReference("Books");
        String name2 = myRef2.child(book_id).getKey();

        //adds user to waitlist if they aren't already on that book's waitlist
        DataSnapshot bookData = Globals.getInstance().books.getData();
        boolean duplicateRequest = false;
        for (DataSnapshot i : bookData.getChildren()) {
            Book item = i.getValue(Book.class);

            //add request to current book only
            if (item.getId().equals(book_id)) {
                //checks if the user has already made a request on this book
                if (item.getRequest() != null) {
                    for (Request r : item.getRequest().values()) {
                        if (r.getUser().getUid().equals(user.getUid())) {
                            duplicateRequest = true;
                        }
                    }
                    if (!duplicateRequest) {
                        Log.d("NEH", "not the first request");

                        //saves the request to database if they haven't already made a request
                        Request request_book = new Request(user, book, myRef2.child(book_id).child("request").child(ts).getKey());
                        myRef2.child(book_id).child("request").child(ts).setValue(request_book);

                        Notification notification_waitlist = new Notification("You Requested a Book", "You've been added to the waitlist for " + item.getTitle(), user);
                        notification_waitlist.save();

                    }
                } else {
                    Log.d("NEH", "else statement");

                    //if this is only request on the book, they are added to the top of the waitlist.
                    Request request_book = new Request(user, book, myRef2.child(book_id).child("request").child(ts).getKey());
                    myRef2.child(book_id).child("request").child(ts).setValue(request_book);

                    //notifies the borrower
                    Request request_notification = new Request(user, book, myRef2.child(book_id).child("request").child(ts).getKey());
                    Notification notification_top = new Notification("You Requested a Book", "You're first in line to receive " + item.getTitle(), user);
                    notification_top.save();

                    //notifies owner since they are automatically at the top of the waitlist
                    Notification notification = new Notification("New Request on Book", user.getName() + " has requested " + item.getTitle(), request_notification, item.getOwner());
                    notification.save();

                }
            }
        }
    }



    /**
     *
     */
    public void setLocation() {
        // TODO
    }

    /**
     *
     * @return date
     */
    public String getDate() {
        return date;
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
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @param ISBN
     */
    public void setISBN(long ISBN) {
        book.setIsbn(ISBN);
    }

    /**
     *
     * @param owneremail
     */
    public void setOwneremail(String owneremail) {
        this.owneremail = owneremail;
    }

    /**
     *
     * @param requesteremail
     */
    public void setRequesteremail(String requesteremail) {
        this.requesteremail = requesteremail;
    }

    /**
     *
     * @param user
     * @param book
     * @param location
     * @param date
     */
    Request(User user, Book book, Location location, String date){
        this.user = user;
        this.book = book;
        this.location = location;
        this.date = date;
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
