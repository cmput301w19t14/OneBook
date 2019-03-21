package ca.ualberta.c301w19t14.onebook;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import java.util.Random;

/**
 * Notification model.
 *
 * Example creating notification and pushing:
 *
 * Notification notification = new Notification(
 *       "Title",
 *       "Content",
 *       Globals.getInstance().users.getData().child(
 *       FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(User.class)
 *    );
 *
 * notification.send(view.getContext());
 *
 * @author Dimitri Trofimuk
 */
public class Notification {

    public final int TYPE_MSG = 1;
    public final int TYPE_REQUEST = 2;
    public final int TYPE_NO_ACTION_REQ = 3;
    private final String CHANNEL_ID = "OneBook";

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

    /**
     * Send the push notification to the user.
     * Returns the user to MainActivity, showing all notifications.
     *
     * @param context Android context
     */
    public void send(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        createNotificationChannel(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_book_black_24dp)
                .setContentTitle(this.title)
                .setContentText(this.content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(this.content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(new Random().nextInt(255), builder.build());

    }

    /**
     * Creates the notification channel.
     * Used by send().
     *
     * @param context
     */
    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getResources().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}