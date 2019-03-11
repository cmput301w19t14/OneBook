package ca.ualberta.c301w19t14.onebook;

import java.util.ArrayList;

/**
 * This class will be used when implementing the waitlist in part 5
 * @author CMPUT 301 Team 14
 * */

public class Waitlist {
    private ArrayList<User> queue;
    private Book book;
    private User borrow;

    /**
     * Constructor
     * @param b Book item
     */
    public Waitlist(Book b)
    {
        this.queue = new ArrayList<User>();
        this.book = b;

    }

    /**
     * setter for book in a wailist item
     * @param book
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * getter for queue
     * @return queue, ArrayList
     */
    public ArrayList<User> getQueue() {
        return queue;
    }

    /**
     * getter for book in a waitlist item
     * @return book
     */
    public Book getBook() {
        return book;
    }

    /**
     * finds the next user in the queue
     * @return borrow
     */
    public User nextinqueue()
    {
        if (queue.size() == 0)
            return null;
        User user = queue.remove(queue.size()-1);

        return user;
    }

    /**
     * finds the next user in the queue
     */
    public User seeNextInQueue(){
        return queue.get(queue.size()-1);
    }

    /**
     * adds a user to a book's queue
     * @param user
     * @return boolean
     */
    public boolean addtoQueue(User user)
    {
        this.queue.add(user);
        return true;
    }

}
