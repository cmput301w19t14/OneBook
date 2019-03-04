package ca.ualberta.c301w19t14.onebook;

import java.util.ArrayList;

public class MessagingSystem {
    private User user;
    private ArrayList<Message> messages;

    public MessagingSystem(){

    }

    public boolean sendMessage( User receivingUser, String message){

        return true;
    }

    public boolean checkNewMessages(){

        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
