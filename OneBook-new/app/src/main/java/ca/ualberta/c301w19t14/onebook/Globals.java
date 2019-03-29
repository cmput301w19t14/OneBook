package ca.ualberta.c301w19t14.onebook;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ca.ualberta.c301w19t14.onebook.models.User;
import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

//save changes to database
public class Globals {
    private static Globals instance;

    //Globals variables in Globals
    public FirebaseUtil books;
    public FirebaseUtil requests;
    public FirebaseUtil users;
    public FirebaseUtil notifications;

    public FirebaseUser user;


    /**
     *
     */
    public void initFirebaseUtil(){
        requests = new FirebaseUtil("Requests");
        books = new FirebaseUtil("Books");
        users = new FirebaseUtil("Users");
        notifications = new FirebaseUtil("notifications");
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

    public static User getCurrentUser() {
        Globals g = Globals.getInstance();
        return g.users.getData().child(g.user.getUid()).getValue(User.class);
    }
}
