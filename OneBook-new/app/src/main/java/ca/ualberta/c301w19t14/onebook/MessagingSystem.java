package ca.ualberta.c301w19t14.onebook;

import java.util.ArrayList;

/**This class will be used in part 5 as part of the messaging functionality
 * @author CMPUT 301 Team 14*/
public class MessagingSystem {
    private User user;
    private ArrayList<Message> messages;

    /**
     *
     */
    public MessagingSystem(){

    }

    /**
     *
     * @param receivingUser
     * @param message
     * @return true
     */
    public boolean sendMessage( User receivingUser, String message){

        return true;
    }

    /**
     *
     * @return true
     */
    public boolean checkNewMessages(){

        return true;
    }

    /**
     *
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
