package ca.ualberta.c301w19t14.onebook;

import java.util.ArrayList;

public class Book {
    private int isbn;
    private String title;
    private String author;
    private String category;
    private ArrayList<Request> requesters;
    private User owner;
    private User borrower;
    private Location location;
    private String status;

    private Waitlist waitlist;

    public Book() {

    }

    //temporary constructor for recycleview test
    public Book(String title, User owner, User borrower) {
        this.title = title;
        this.owner = owner;
        this.borrower = borrower;
    }

    public Book(int Isbn,String Title,String author,
                     String Category,ArrayList<Request> requesters,
                     Owner owner,Borrower borrow,Location location, String status)
    {
        this.isbn =Isbn;
        this.title = Title;
        this.author = author;
        this.category = Category;
        this.requesters = requesters;
        this.owner = owner;
        this.borrower=borrow;
        this.location =location;
        this.status = status;
    }
    public int getIsbn() { return isbn; }
    public void setIsbn(int isbn) { this.isbn = isbn; }
    public String getTitle() { return title; }
    public void setTitle(String Title) { this.title = Title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.title = category; }
    public String getAuthor() { return this.author; }
    public void setAuthor(String author) { this.author = author; }
    public void setLocation(Location location) { this.location = location; }
    public Location getLocation() { return location; }
    public User getOwner() { return owner; }
    public User getBorrower(){return borrower; }
    public void setOwner(Owner owner) { this.owner = owner; }

    public void setRequesters(Request requesters) {this.requesters.add(requesters); }
    public ArrayList<Request> getRequesters() {return requesters; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Waitlist getWaitlist() { return waitlist; }
    public void setWaitlist(Waitlist waitlist) { this.waitlist = waitlist; }

    public boolean createViaISBNPhoto()
    {
        //code to be added later
        return false;
    }
    public void lendToNext()
    {
        //
    }
    public void addToWait(User user)
    {
        //
    }
}