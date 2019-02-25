package com.example.onebook;

public class Request {

    private Location location;
    private Owner owner;
    private Borrower borrower;
    private Book book;

    Request(Owner owner, Borrower borrower, Book book){
        setOwner(owner);
        setBorrower(borrower);
        setBook(book);
    }

    public boolean sendNotification() {

        boolean is_success = true;

        return is_success;

    }

    public boolean setLocation(Location location) {

        boolean is_success = true;

        this.location = location;

        return is_success;
    }

    public boolean setOwner(Owner owner) {

        boolean is_success = true;

        this.owner = owner;

        return is_success;
    }

    public boolean setBorrower(Borrower borrower) {

        boolean is_success = true;

        this.borrower = borrower;

        return is_success;
    }

    public boolean setBook(Book book) {

        boolean is_success = true;

        this.book = book;

        return is_success;
    }

    public Book getBook() {
        return book;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public Owner getOwner() {
        return owner;
    }

    public Location getLocation() {
        return location;
    }
}
