package ca.ualberta.c301w19t14.onebook.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.activities.MapsActivity;
import ca.ualberta.c301w19t14.onebook.activities.ViewBookActivity;
import ca.ualberta.c301w19t14.onebook.activities.ViewRequestsActivity;
import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.models.Notification;
import ca.ualberta.c301w19t14.onebook.models.Request;

/**
 * requests book
 * request was accepted
 * messages
 *
 * Handles the request process
 */
public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestsViewHolder> {

    public View view;
    private ArrayList<Request> list;
    private Context mContext;

    public RequestsAdapter(Context context, ArrayList<Request> list) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(RequestsViewHolder mVh, int i) {
        Request request = list.get(i);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(request.getId()) * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        mVh.request = request;
        mVh.title.setText(request.getUser().getName());
        mVh.content.setText(formatter.format(calendar.getTime()) + " - " + request.getStatus());

    }

    @Override
    public RequestsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.request_list_item, viewGroup, false);

        return new RequestsViewHolder(itemView, i);
    }

    public class RequestsViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView content;
        Request request;

        RequestsViewHolder(View view, int i) {
            super(view);
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
            view.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(final View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                    alertDialog.setTitle("Accept/Reject");
                    alertDialog.setMessage("What would you like to do?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ACCEPT",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    //update request status to accepted
                                    Book book = request.getBook();
                                    FirebaseDatabase.getInstance().getReference("Books").child(book.getId()).child("request").child(request.getId()).child("status").setValue("Accepted");

                                    //notify borrower that their request has been accepted
                                    //TODO: how do they find out the location to meet up?
                                    Notification accept_notification = new Notification("Request Accepted", book.getOwner().getName() + " has accepted your request on " + book.getTitle(), request.getUser());
                                    accept_notification.save();

                                    //notify borrower that they need to meet up with the owner
                                    Notification trade_notification_borrower = new Notification("Meet up Required", "You need to meet " + book.getOwner().getName() + " to pick up " + book.getTitle(), request.getUser());
                                    trade_notification_borrower.save();

                                    //notify owner that they need to meet up with borrower
                                    Notification trade_notification_owner = new Notification("Meet up Required", "You need to meet " + request.getUser().getName()+ " to give them " + book.getTitle(), book.getOwner());
                                    trade_notification_owner.save();

                                    // TODO: look for notifications, and delete.
                                    dialog.dismiss();

                                    // Maps
                                    Intent intent = new Intent(v.getContext(), MapsActivity.class);
                                    intent.putExtra("book_id", book.getId());
                                    v.getContext().startActivity(intent);

                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "REJECT",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Book book = request.getBook();
                                    //if they reject a request.
                                    //create a new notification for the person who was rejected
                                    Notification reject_notification = new Notification("Request Rejected", book.getOwner().getName() + " has rejected your request on " + book.getTitle(), request.getUser());
                                    reject_notification.save();

                                    // TODO: look for notifications, and delete.
                                    FirebaseDatabase.getInstance().getReference("Books").child(book.getId()).child("request").child(request.getId()).removeValue();

                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

            });
        }
    }
}