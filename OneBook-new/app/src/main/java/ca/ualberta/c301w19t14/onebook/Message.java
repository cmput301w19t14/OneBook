package com.example.onebook;

public class Message {
    private String user;
    private String message;
    private String date;

    public void setUser(String user) {
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public Message (String user, String message, String date){
        this.user = user;
        this.message = message;
        this.date = date;
    }
}
