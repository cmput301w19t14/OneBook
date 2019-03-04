package ca.ualberta.c301w19t14.onebook;

import java.util.ArrayList;

public class Book {
    private int Isbn;
    private String Title;
    private String author;
    private String Category;
    private ArrayList<Request> requesters;
    private Owner owner;
    private Borrower borrow;
    private Location location;
    private String status;



    private Waitlist waitlist;

    public Book(int Isbn,String Title,String author,
                     String Category,ArrayList<Request> requesters,
                     Owner owner,Borrower borrow,Location location, String status)
    {
        this.Isbn =Isbn;
        this.Title = Title;
        this.author = author;
        this.Category = Category;
        this.requesters = requesters;
        this.owner = owner;
        this.borrow=borrow;
        this.location =location;
        this.status = status;
    }
    public int get_ISBN() { return Isbn; }
    public void Set_ISBN(int isbn) { this.Isbn = isbn; }
    public String Get_title() { return Title; }
    public void Set_Title(String Titile) { this.Title = Titile; }
    public String Get_Category() { return Category; }
    public void Set_Category(String category) { this.Title = category; }
    public String Get_author() { return this.author; }
    public void Set_Author(String author) { this.author = author; }
    public void setOwner(Owner owner) { this.owner = owner; }
    public void Set_Location(Location location) { this.location = location; }
    public Location Get_location() { return location; }
    public void setBorrow(Borrower borrow) { this.borrow = borrow; }
    public Owner getOwner() { return owner; }
    public Borrower getBorrow() { return borrow; }
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
