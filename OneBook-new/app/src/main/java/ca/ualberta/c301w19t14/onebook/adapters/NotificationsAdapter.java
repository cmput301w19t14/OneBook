package ca.ualberta.c301w19t14.onebook.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.models.Notification;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.Book;

/**
 * requests book
 * request was accepted
 * messages
 *
 * Handles the request process
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {

    public View view;
    private ArrayList<Notification> notificationList;
    private Context mContext;

    public NotificationsAdapter(Context context, ArrayList<Notification> notificationList) {
        this.notificationList = notificationList;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    @Override
    public void onBindViewHolder(NotificationsViewHolder mVh, int i) {
        Notification notification = notificationList.get(i);

        mVh.notification = notification;
        mVh.title.setText(notification.getTitle());
        mVh.content.setText(notification.getContent());

    }

    @Override
    public NotificationsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.request_list_item, viewGroup, false);

        return new NotificationsViewHolder(itemView, i);
    }

    public class NotificationsViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView content;
        Notification notification;

        NotificationsViewHolder(View view, int i) {
            super(view);

            if(notification.getRequest() == null && notification.getRequest().getBook().getOwner().getUid().equals(Globals.getInstance().user.getUid())) {
                // This notification is for an owner of a book, notifying them of a new actionable request.

                view.setOnClickListener(new View.OnClickListener() {

                    @Override public void onClick(View v){
                        AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                        alertDialog.setTitle("Accept/Reject");
                        alertDialog.setMessage("What would you like to do?");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ACCEPT",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        notification.getRequest().setStatus("Accepted");
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("Requests");
                                        myRef.child(notification.getRequest().getId()).setValue(notification.getRequest());

                                        Book book = notification.getRequest().getBook();
                                        book.setStatus("Borrowed");
                                        book.setBorrower(notification.getRequest().getUser());
                                        myRef = database.getReference("Books");
                                        myRef.child(notification.getRequest().getBook().getId()).setValue(notification.getRequest().getBook());

                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "REJECT",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        notification.getRequest().setStatus("Rejected");
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("Requests");
                                        myRef.child(notification.getRequest().getId()).setValue(notification.getRequest());

                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                });
            } else {
                // delete notification
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Notifications");
                db.child(notification.getId()).removeValue();

                Toast.makeText(mContext, "Notification removed.", Toast.LENGTH_SHORT).show();
            }


        }
    }
}