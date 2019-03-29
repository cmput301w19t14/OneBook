package ca.ualberta.c301w19t14.onebook.models;

/**
 * This class is a model for the chatList in Firebase.
 * @author jandaile CMPUT 301 team 14
 * @since 2019-03-29
 * @version 1.0
 */
public class MessagingChatsList {
    private String id;

    /**
     * Empty constructor for Firebase
     */
    public MessagingChatsList(){

    }
    /**
     * Constructor for MessagingChatList in Firebase
     * @param id: chatList id in database
     */
    public MessagingChatsList(String id){

        this.id = id;
    }

    /**
     * getter for the id of chatList
     * @return id of chatList in Firebase
     */
    public String getId(){
        return id;
    }

    /**
     * setter for chatList id
     * @param id: id to set for chatList
     */
    public void setId(String id){

        this.id = id;
    }
}
