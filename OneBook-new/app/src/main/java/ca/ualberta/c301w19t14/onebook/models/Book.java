package ca.ualberta.c301w19t14.onebook.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ca.ualberta.c301w19t14.onebook.Globals;

/** Handles all attributes of the book object.
 * @author CMPUT 301 Team 14
 * */
public class Book {
    private String id;
    private long isbn;
    private String description;
    private String title;
    private String author;
    private HashMap<String, Request> request;
    private User owner;
    private User borrower;
    private String status;

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
        this.status = "Available";
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
     * getter for book status
     * @return status
     */
    public String getStatus() { return status; }

    /**
     * setter for book status
     * @param status
     */
    public void setStatus(String status) { this.status = status; }

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
        if(this.getAcceptedRequest() != null) {
            return (!this.getAcceptedRequest().getUser().getUid().equals(Globals.getInstance().user.getUid()) && !this.userIsOwner());
        } else {
            return (!this.userIsOwner());
        }
    }
}
