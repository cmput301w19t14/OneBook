package ca.ualberta.c301w19t14.onebook;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Notification model.
 *
 * @author Dimitri Trofimuk
 */
public class Notification {
    private String id;
    private String title;
    private String content;

    @Nullable
    private Request request = null;

    @NonNull
    private User user;

    /**
     * Empty constructor for Firebase.
     */
    Notification() {

    }

    /**
     * Constructor for basic notification.
     *
     * @param title notification title
     * @param content notification message
     * @param user user receiving notification
     */
    Notification(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    /**
     * Constructor for notification for a book request.
     *
     * @param title notification title
     * @param content notification message
     * @param request book request
     * @param user user receiving notification
     */
    Notification(String title, String content, Request request, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.request = request;
    }

    /**
     * Saves the notification to the database.
     */
    public void save() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Notifications");
        this.setId(db.push().getKey());

        db.child(this.getId()).setValue(this);
    }

    /**
     * Gets the notification ID.
     *
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the notification ID.
     * Should be Firebases push().
     *
     * @param id push() value
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for title.
     *
     * @return title notification title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Getter for content.
     *
     * @return content notification content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Getter for request.
     *
     * @return request notification request or null
     */
    @Nullable
    public Request getRequest() {
        return this.request;
    }

    /**
     * Getter for user.
     *
     * @return user user receiving notification
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Setter for content.
     *
     * @param content notification content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Setter for request.
     *
     * @param request notification request or null
     */
    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * Setter for user.
     *
     * @param user user receiving notification
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter for title.
     *
     * @param title notification title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}