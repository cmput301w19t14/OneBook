package ca.ualberta.c301w19t14.onebook;

import java.util.ArrayList;

public class Waitlist {


    private ArrayList<User> queue;
    private Book book;
    private User borrow;

    public Waitlist(ArrayList<User> q,Book b)
    {

    }
    public void addQueue(User queue) {
        this.queue.add(queue);
    }

    public void setBook(Book book) {
        this.book = book;
    }
    public ArrayList<User> getQueue() {
        return queue;
    }

    public Book getBook() {
        return book;
    }
    public User nextinqueue()
    {
        //code later

        return borrow;
    }
    public boolean addtoQueue(User user)
    {
        this.queue.add(user);
        return true;
    }
    public boolean notifyUsers(User borrower,User owner)
    {
        //code later
        return true;

    }

}
