package ca.ualberta.c301w19t14.onebook.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * This class is a model for the notifications.
 * @author CMPUT301 Team14: Dimitri T
 * @see ca.ualberta.c301w19t14.onebook.adapters.NotificationsAdapter
 * @version 1.0
 */
public class Notification {

    final static public int UP = 1;
    final static public int DOWN = 2;
    final static public int BOOK = 3;
    final static public int MESSAGE = 4;
    final static public int COMPASS = 5;
    final static public int MARKER = 6;
    final static public int ROCKET = 7;
    private String id;
    private String title;
    private String content;
    private Long date;
    private int icon;

    @Nullable
    private Request request = null;

    @NonNull
    private User user;

    /**
     * Empty constructor for Firebase.
     */
    public Notification() {

    }

    /**
     * Constructor for basic notification.
     *
     * @param title   notification title
     * @param content notification message
     * @param user    user receiving notification
     */
    public Notification(String title, String content, User user, int icon) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.icon = icon;
    }

    /**
     * Constructor for notification for a book request.
     * @param title   notification title
     * @param content notification message
     * @param request book request
     * @param user    user receiving notification
     * @param icon    icon to show
     */
    public Notification(String title, String content, Request request, User user, int icon) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.request = request;
        this.icon = icon;
    }

    /**
     * Saves the notification to the database.
     */
    public void save() {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Notifications").child(this.user.getUid());
        this.setId(db.push().getKey());

        db.child(this.getId()).setValue(this);
    }

    public void delete() {
        FirebaseDatabase.getInstance().getReference("Notifications").child(this.getUser().getUid()).child(this.getId()).removeValue();
    }

    public static void deleteWhereRequestAndUser(final Request request, User user) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Notifications").child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Request r = s.getValue(Notification.class).getRequest();
                    if (r != null && r.getId().equals(request.getId())) {
                        s.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Gets the notification ID.
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the notification ID.
     * Should be Firebases push().
     * @param id push() value
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for title.
     * @return title notification title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Getter for content.
     * @return content notification content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Getter for request.
     * @return request notification request or null
     */
    @Nullable
    public Request getRequest() {
        return this.request;
    }

    /**
     * Getter for user.
     * @return user user receiving notification
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Setter for content.
     * @param content notification content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Setter for request.
     * @param request notification request or null
     */
    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * Setter for user.
     * @param user user receiving notification
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter for title.
     * @param title notification title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for icon.
     * @param icon notification icon
     */
    public void setIcon(int icon) {
        this.icon = icon;
    }

    /**
     * Getter for int.
     * @return int icon int
     */
    public int getIcon() {
        return this.icon;
    }
}