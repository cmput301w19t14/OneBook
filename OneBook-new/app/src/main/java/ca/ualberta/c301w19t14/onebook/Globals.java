package ca.ualberta.c301w19t14.onebook;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ca.ualberta.c301w19t14.onebook.models.User;
import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

/**
 * This class saves some Firebase attributes.
 * @author CMPUT301 Team14: Dustin M
 * @version 1.0
 */
public class Globals {
    private static Globals instance;

    //Globals variables in Globals
    public FirebaseUtil books;
    public FirebaseUtil requests;
    public FirebaseUtil users;
    public FirebaseUtil notifications;
    public FirebaseUtil chats;
    public FirebaseUtil chatlist;

    public FirebaseUser user;


    /**
     * Initializes Firebase utility for the data paths.
     */
    public void initFirebaseUtil(){
        requests = new FirebaseUtil("Requests");
        books = new FirebaseUtil("Books");
        users = new FirebaseUtil("Users");
        notifications = new FirebaseUtil("notifications");
        chats = new FirebaseUtil("chats");
        chatlist = new FirebaseUtil("chatlist");
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

    /**
     * Get the current user that is log on in the app
     * @return logged in user
     */
    public static User getCurrentUser() {
        Globals g = Globals.getInstance();
        return g.users.getData().child(g.user.getUid()).getValue(User.class);
    }
}
