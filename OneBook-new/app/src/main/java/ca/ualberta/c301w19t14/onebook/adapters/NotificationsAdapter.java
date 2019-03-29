package ca.ualberta.c301w19t14.onebook.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.models.Notification;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.models.Request;

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
        Log.e("TEST", notification.getContent());

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
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
                view.setOnClickListener(new View.OnClickListener() {

                    @Override public void onClick(View v) {
                        if (notification.getRequest() != null && notification.getRequest().getBook().getOwner().getUid().equals(Globals.getInstance().user.getUid())) {
                            // This notification is for an owner of a book, notifying them of a new actionable request.
                            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                            alertDialog.setTitle("Accept/Reject");
                            alertDialog.setMessage("What would you like to do?");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ACCEPT",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            //notification.getRequest().setStatus("Accepted");
                                            //FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            //DatabaseReference myRef = database.getReference("Requests");
                                            //myRef.child(notification.getRequest().getId()).setValue(notification.getRequest());


                                            //myRef2.child(book_id).child("request").child(ts).setValue(request_book);


                                            //update request status to accepted
                                            Book book = notification.getRequest().getBook();
                                            Request request = notification.getRequest();

                                            FirebaseDatabase.getInstance().getReference("Books").child(book.getId()).child("request").child(request.getId()).child("book").child("status").setValue("Accepted");

                                            //book.child(book.getId()).child("request").child().setStatus("Accepted");
                                            //book.setBorrower(notification.getRequest().getUser());
                                            //myRef = database.getReference("Books");
                                            //myRef.child(notification.getRequest().getBook().getId()).setValue(notification.getRequest().getBook());

                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "REJECT",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            //if they reject a request.
                                            //create a new notification for the person who was rejected
                                            Notification reject_notification = new Notification("Request Rejected", notification.getUser().getName() + " has rejected your request on " + notification.getRequest().getBook().getTitle(), notification.getRequest().getUser());
                                            reject_notification.save();

                                            //deletes the original notification for current user
                                            notification.delete();

                                            //deletes the request from the book database
                                            Book book = notification.getRequest().getBook();
                                            Request request = notification.getRequest();
                                            FirebaseDatabase.getInstance().getReference("Books").child(book.getId()).child("request").child(request.getId()).removeValue();

                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        } else {

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