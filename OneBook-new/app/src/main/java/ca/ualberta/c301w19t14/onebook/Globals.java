package ca.ualberta.c301w19t14.onebook;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

//save changes to database
public class Globals {
    private static Globals instance;

    //Globals variables in Globals
    public FirebaseUtil books;
    public FirebaseUtil requests;
    public FirebaseUtil users;

    public FirebaseUser user;

    /**
     *
     */
    public void initFirebaseUtil(){
        requests = new FirebaseUtil("Requests");
        books = new FirebaseUtil("Books");
        users = new FirebaseUtil("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     *
     * @return instance
     */
    public static synchronized Globals getInstance(){
        if(instance == null){
            instance = new Globals();
        }
        return instance;
    }
}
