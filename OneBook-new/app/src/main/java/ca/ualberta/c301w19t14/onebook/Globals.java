package ca.ualberta.c301w19t14.onebook;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

public class Globals {
    private static Globals instance;

    //Globals variables in Globals
    public FirebaseUtil books;
    public FirebaseUtil requests;

    public void initFirebaseUtil(){
        books = new FirebaseUtil("Books");
        requests = new FirebaseUtil("Requests");
    }

    public static synchronized Globals getInstance(){
        if(instance == null){
            instance = new Globals();
        }
        return instance;
    }
}
