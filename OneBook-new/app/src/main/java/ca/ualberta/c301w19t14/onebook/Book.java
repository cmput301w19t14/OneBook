package ca.ualberta.c301w19t14.onebook;

import java.util.ArrayList;

/** Handles all attributes of the book object.
 * @author CMPUT 301 Team 14
 * */
public class Book {
    private String id;
    private long isbn;
    private String title;
    private String author;
    private String category;
    private ArrayList<Request> requesters;
    private User owner;
    private User borrower;
    private Location location;
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

    private String description;

    private Waitlist waitlist;

    /**
     *
     */
    public Book() {

    }

    /**temporary constructor for recycleview test
     * @param title
     * @param owner
     * @param borrower
     */
    public Book(String title, User owner, User borrower) {
        this.title = title;
        this.owner = owner;
        this.borrower = borrower;
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
     * @param Isbn
     * @param Title
     * @param author
     * @param Category
     * @param owner
     * @param borrow
     * @param location
     * @param status
     */
    public Book(long Isbn,String Title,String author,
                     String Category,
                User owner,User borrow,Location location, String status)
    {
        this.isbn =Isbn;
        this.title = Title;
        this.author = author;
        this.category = Category;
        this.owner = owner;
        this.borrower=borrow;
        this.location =location;
        this.status = status;
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
     * getter for book category
     * @return category
     */
    public String getCategory() { return category; }

    /**
     * setter for book category
     * @param category
     */
    public void setCategory(String category) { this.title = category; }

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
     * setter for book location
     * @param location
     */
    public void setLocation(Location location) { this.location = location; }

    /**
     * getter for book location
     * @return location
     */
    public Location getLocation() { return location; }

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

    /**
     * setter for book requesters
     * @param requesters
     */
    public void setRequesters(Request requesters) {this.requesters.add(requesters); }

    /**
     * getter for book requesters
     * @return requesters
     */
    public ArrayList<Request> getRequesters() {return requesters; }

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

    /**
     * getter for book waitlist
     * @return waitlist
     */
    public Waitlist getWaitlist() { return waitlist; }

    /**
     * setter for book waitlist
     * @param waitlist
     */
    public void setWaitlist(Waitlist waitlist) { this.waitlist = waitlist; }

    /**
     * create a book based on a photo of an ISBN
     * @return boolean
     */
    public boolean createViaISBNPhoto()
    {
        //code to be added later
        return false;
    }

    /**
     * lend an available book to the first person in the queue
     */
    public void lendToNext()
    {
        //
    }

    /**
     * add a user to the end of the waitlist queue
     * @param user
     */
    public void addToWait(User user)
    {
        //
    }
}
