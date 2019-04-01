package ca.ualberta.c301w19t14.onebook.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.activities.MapsActivity;
import ca.ualberta.c301w19t14.onebook.models.Notification;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.models.Request;

import ca.ualberta.c301w19t14.onebook.fragments.NotificationFragment;

/**
 * RecyclerView adapter for notifications.
 * Handles displaying and clicking on notifications.
 * Future improvements include having location for a meet up show when a meet up notification is clicked.
 * @author CMPUT301 Team14: Natalie H, Dimitri T
 * @see NotificationFragment
 * @version 1.0
 *
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    public View view;
    private ArrayList<Notification> notificationList;
    private Context mContext;

    public NotificationsAdapter(Context context, ArrayList<Notification> notificationList) {
        this.notificationList = notificationList;
        this.mContext = context;
    }

    /**
     *
     * @return the total number of notifications the user has.
     */
    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    /**
     * Attaches notification content to the view.
     *
     * @param mVh
     * @param i
     */
    @Override
    public void onBindViewHolder(NotificationsViewHolder mVh, int i) {
        Notification notification = notificationList.get(i);
        Log.e("TEST", notification.getContent());

        mVh.notification = notification;
        mVh.title.setText(notification.getTitle());
        mVh.content.setText(notification.getContent());

        switch(notification.getIcon()) {
            case Notification.UP:
                mVh.icon.setImageResource(R.drawable.arrowup64);
                break;
            case Notification.DOWN:
                mVh.icon.setImageResource(R.drawable.arrowdown64);
                break;
            case Notification.BOOK:
                mVh.icon.setImageResource(R.drawable.booklet64);
                break;
            case Notification.MESSAGE:
                mVh.icon.setImageResource(R.drawable.mail64);
                break;
            case Notification.COMPASS:
                mVh.icon.setImageResource(R.drawable.compass64);
                break;
            case Notification.MARKER:
                mVh.icon.setImageResource(R.drawable.location64);
                break;
            case Notification.ROCKET:
                mVh.icon.setImageResource(R.drawable.rocket64);
                break;
            default:
                mVh.icon.setImageResource(R.drawable.rocket64);
                break;
        }
    }

    @Override
    @NonNull
    public NotificationsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.notification_list_item, viewGroup, false);

        return new NotificationsViewHolder(itemView, i);
    }

    public class NotificationsViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView content;
        protected ImageView icon;
        Notification notification;

        NotificationsViewHolder(View view, int i) {
            super(view);
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
            icon = view.findViewById(R.id.icon);
                view.setOnClickListener(new View.OnClickListener() {

                    @Override public void onClick(final View v) {
                        if (notification.getRequest() != null && notification.getRequest().getBook().getOwner().getUid().equals(Globals.getInstance().user.getUid())) {
                            // This notification is for an owner of a book, notifying them of a new actionable request.
                            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                            alertDialog.setTitle("Accept/Reject");
                            alertDialog.setMessage("What would you like to do?");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ACCEPT",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            Request request = notification.getRequest();
                                            request.accept();

                                            //deletes the original notification for owner
                                            notification.delete();

                                            dialog.dismiss();

                                            // they need to select a location
                                            Intent intent = new Intent(v.getContext(), MapsActivity.class);
                                            intent.putExtra("book_id", request.getBook().getId());
                                            v.getContext().startActivity(intent);
                                        }
                                    });
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "REJECT",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Request request = notification.getRequest();

                                            //deletes the original notification for current user
                                            notification.delete();

                                            request.reject();

                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        } else {
                            // TODO: checks if it's a notification for a meet up. displays location if it is

                            //other notifications can be deleted
                            AlertDialog alertDialog2 = new AlertDialog.Builder(v.getContext()).create();
                            alertDialog2.setTitle("Notification");
                            alertDialog2.setMessage("What would you like to do?");
                            alertDialog2.setButton(AlertDialog.BUTTON_POSITIVE, "DELETE",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            notification.delete();
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog2.setButton(AlertDialog.BUTTON_NEGATIVE, "SAVE",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog2.show();
                        }
                    }

                });
        }
    }
}