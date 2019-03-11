package ca.ualberta.c301w19t14.onebook;

import java.util.ArrayList;

public class Waitlist {


    private ArrayList<User> queue;
    private Book book;
    private Borrower borrow;

    public Waitlist(Book b)
    {
        this.queue = new ArrayList<User>();
        this.book = b;

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
        if (queue.size() == 0)
            return null;
        User user = queue.remove(queue.size()-1);

        return user;
    }

    public User seeNextInQueue(){
        return queue.get(queue.size()-1);
    }
    public boolean addtoQueue(User user)
    {
        this.queue.add(user);
        return true;
    }
    public boolean notifyUsers(Borrower borrower,Owner owner)
    {
        //code later
        return true;

    }

}
