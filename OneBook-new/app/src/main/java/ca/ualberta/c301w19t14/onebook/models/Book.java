package ca.ualberta.c301w19t14.onebook.models;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ca.ualberta.c301w19t14.onebook.Globals;

/** Handles all attributes of the book object.
 * @author CMPUT 301 Team 14
 * */
public class Book {
    final public static String AVAILABLE = "Available";
    final public static String REQUESTED = "Requested";
    final public static String ACCEPTED = "Accepted";
    final public static String BORROWED = "Borrowed";
    private String id;
    private long isbn;
    private String description;
    private String title;
    private String author;
    private HashMap<String, Request> request;
    private User owner;
    private User borrower;

    /**
     * getter for description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     */
    public Book() {
    }

    /**
     *
     * @param isbn
     * @param title
     * @param author
     * @param description
     * @param owner
     */
    public Book(long isbn, String title, String author, String description, User owner) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.description = description;
        this.owner = owner;
        this.request = new HashMap<>();
    }

    /**
     * getter for Book Id
     * @return this.id
     */
    public String getId() {
        return this.id;
    }

    /**
     * setter for Book Id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getter for book ISBN
     * @return isbn
     */
    public long getIsbn() { return isbn; }

    /**
     * setter for book ISBN
     * @param isbn
     */
    public void setIsbn(long isbn) { this.isbn = isbn; }

    /**
     * getter for Book Title
     * @return title
     */
    public String getTitle() { return title; }

    /**
     * setter for Book Title
     * @param Title
     */
    public void setTitle(String Title) { this.title = Title; }

    /**
     * getter for book author
     * @return this.author
     */
    public String getAuthor() { return this.author; }

    /**
     * setter for book author
     * @param author
     */
    public void setAuthor(String author) { this.author = author; }

    /**
     * getter for book owner
     * @return owner
     */
    public User getOwner() { return owner; }

    /**
     * getter for book borrower
     * @return
     */
    public User getBorrower(){return borrower; }

    /**
     * setter for book owner
     * @param owner
     */
    public void setOwner(User owner) { this.owner = owner; }
    public void setBorrower(User borrower) { this.borrower = borrower; }

    /**
     * setter for book requesters
     * @param request
     */
    public void setRequest(HashMap<String, Request> request) {this.request = request; }

    /**
     * getter for book requesters
     * @return requesters
     */
    public HashMap<String, Request> getRequest() {return request; }

    /**
     * Returns the status for the book.
     *  * Owners can see AVAILABLE or BORROWED.
     *  * Users can see AVAILABLE, BORROWED, ACCEPTED or REQUESTED.
     *
     * Most of the time, the status should be AVAILABLE.
     *
     * @return status
     */
    public String getStatus() {
        if(this.userIsOwner()) {
            if(this.borrower != null) {
                return Book.BORROWED;
            }
        } else {
            if(this.userHasRequest(Globals.getCurrentUser()) && this.getAcceptedRequest().getUser() == Globals.getCurrentUser()) {
                return Book.ACCEPTED;
            } else if(this.userHasRequest(Globals.getCurrentUser())) {
                return Book.REQUESTED;
            } else if(this.borrower == Globals.getCurrentUser()) {
                return Book.BORROWED;
            }
        }

        return Book.AVAILABLE;
    }

    /**
     * Returns the accepted request on the book, if exists.
     *
     * @return Request|null
     */
    public Request getAcceptedRequest() {
        if(this.getRequest() != null) {
            for (Request r : this.getRequest().values()) {
                if (r.getStatus().equals(Request.ACCEPTED)) {
                    return r;
                }
            }
        }
        return null;
    }

    /**
     * Returns if the user has a request on the book.
     *
     * @param user User
     * @return boolean
     */
    private boolean userHasRequest(User user) {
        if(this.getRequest() != null) {
            for (Request r : this.getRequest().values()) {
                if (r.getUser().getUid().equals(user.getUid())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Finds a book by it's ID.
     *
     * @param id string book id
     * @return Book a book object
     */
    public static Book find(String id) {
        return Globals.getInstance().books.getData().child(id).getValue(Book.class);
    }

    /**
     * Deletes a book.
     */
    public void delete() {
        FirebaseDatabase.getInstance().getReference("Books").child(this.getId()).removeValue();
    }

    /**
     * Updates a book.
     */
    public void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Books");
        myRef.child(this.getId()).setValue(this);
    }

    /**
     * Returns if the current user is the books owner.
     *
     * @return boolean
     */
    public boolean userIsOwner() {
        return this.getOwner().getUid().equals(Globals.getInstance().user.getUid());
    }

    /**
     * Returns if the current user can request the book.
     *
     * @return boolean
     */
    public boolean userCanRequest() {
        return (!this.userHasRequest(Globals.getCurrentUser()) && !this.userIsOwner());
    }
}
