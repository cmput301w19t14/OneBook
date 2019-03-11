package ca.ualberta.c301w19t14.onebook;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

public class Globals {
    private static Globals instance;

    //Globals variables in Globals
    public FirebaseUtil books;
    public FirebaseUtil requests;
    public FirebaseUtil users;

    public void initFirebaseUtil(){
        requests = new FirebaseUtil("Requests");
        books = new FirebaseUtil("Books");
        users = new FirebaseUtil("Users");
    }

    public static synchronized Globals getInstance(){
        if(instance == null){
            instance = new Globals();
        }
        return instance;
    }
}
