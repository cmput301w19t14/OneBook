package ca.ualberta.c301w19t14.onebook.models;

/**
 * This class is a model for messages.
 * Will be implemented in part 5 as part of the messaging functionality
 * @author CMPUT 301 Team 14*/
public class Message {
    private String user;
    private String message;
    private String date;

    /**
     *
     * @param user
     * @param message
     * @param date
     */
    public Message (String user, String message, String date){
        this.user = user;
        this.message = message;
        this.date = date;
    }
    /**
     * setter for the target user of a message
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * setter for the message content
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * setter for the message date
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * getter for the target user of a message
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * getter for the message content
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * getter for the message date
     * @return date
     */
    public String getDate() {
        return date;
    }

}
