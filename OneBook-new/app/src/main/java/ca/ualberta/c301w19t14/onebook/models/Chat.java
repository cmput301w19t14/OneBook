package ca.ualberta.c301w19t14.onebook.models;

/**
 * This class is a model for a Chat in Firebase.
 * @author jandaile CMPUT 301 team 14
 * @since 2019-03-29
 * @version 1.0
 */
public class Chat {

    private String sender;
    private String receiver;
    private String message;

    /**
     * Empty constructor for Firebase
     */
    public Chat(){

    }
    /**
     * Constructor for a Chat messaging in Firebase
     * @param sender: current user id
     * @param receiver: user id that the message is being sent to
     * @param message: message being sent
     */
    public Chat(String sender, String receiver, String message){
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * getter for sender id
     * @return sender id in Firebase
     */
    public String getSender() {
        return sender;
    }

    /**
     * setter for sender id
     * @param sender: current user id in Firebase
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * getter for receiver id
     * @return receiver id in Firebase
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * setter for receiver id
     * @param receiver: receiver's id in Firebase
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * getter for message String
     * @return message String in Firebase
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter for the message String
     * @param message: message String in Firebase
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
