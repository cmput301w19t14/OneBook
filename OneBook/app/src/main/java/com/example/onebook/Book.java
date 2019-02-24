package com.example.onebook;

import java.util.ArrayList;

public class Book {
    private int Isbn;
    private String Title;
    private String author;
    private String Category;
    private ArrayList<Borrower> requesters;
    private Owner owner;
    private Borrower borrow;
    private Location location;

    public void Book() { }
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
    public void setRequesters(Borrower requesters) {this.requesters.add(requesters); }
    public ArrayList<Borrower> getRequesters() {return requesters; }
    public boolean createViaISBNPhoto()
    {
        //code to be added later
        return false;
    }
}
