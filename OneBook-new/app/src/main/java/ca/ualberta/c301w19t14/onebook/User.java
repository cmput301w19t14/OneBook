package ca.ualberta.c301w19t14.onebook;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.util.FirebaseUtil;

public class User {

    private String uid;
    private String name;
    private String email;

    public User() {
    }

    public User( String uid, String name, String email){
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public static void updateDatabase(final String firebaseUid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myRef.child(firebaseUid).setValue(new User(firebaseUid, user.getDisplayName(), user.getEmail()));
    }


    public static void updateEmail(final String firebaseUid, String new_email) {
        FirebaseAuth.getInstance().getCurrentUser().updateEmail(new_email);
        updateDatabase(firebaseUid);
    }

    public static void updateName(final String firebaseUid, String new_name) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(new_name).build());
        updateDatabase(firebaseUid);
    }

    public static void updatePassword(final String firebaseUid, String new_password) {
        FirebaseAuth.getInstance().getCurrentUser().updatePassword(new_password);
        updateDatabase(firebaseUid);
    }

    protected boolean changeUsername(String username) {
        boolean is_success = false;

        return is_success;
    }

    protected boolean changePassword(String password) {
        boolean is_success = false;

        return is_success;
    }

    public void editContactInfo( String email, long phone) {
        boolean is_success = false;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid(){ return this.uid; }

    public String getName(){ return this.name; }

    public String getEmail() {
        return email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }
}

