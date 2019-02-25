package com.example.onebook;

import java.util.ArrayList;

public class MessagingSystem {
    User user;
    ArrayList<Message> messages;

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
