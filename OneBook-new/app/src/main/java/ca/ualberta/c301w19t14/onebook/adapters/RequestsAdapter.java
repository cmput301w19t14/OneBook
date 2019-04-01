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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.activities.MapsActivity;
import ca.ualberta.c301w19t14.onebook.activities.ViewRequestsActivity;
import ca.ualberta.c301w19t14.onebook.models.Notification;
import ca.ualberta.c301w19t14.onebook.models.Request;

/**
 * RecyclerView adapter for requests.
 * Handles displaying and clicking on requests, in ViewRequestsActivity.
 * @author CMPUT301 Team14: Dimitri T, Natalie H
 * @version 1.0
 * @see ViewRequestsActivity
 */
public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestsViewHolder> {

    public View view;
    private ArrayList<Request> list;
    private Context mContext;

    public RequestsAdapter(Context context, ArrayList<Request> list) {
        this.list = list;
        this.mContext = context;
    }

    /**
     *
     * @return the total number of requests for the books
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Attaches content to the each request in the view.
     *
     * @param mVh
     * @param i
     */
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
                                    request.accept();
                                    Notification.deleteWhereRequestAndUser(request, request.getBook().getOwner());

                                    dialog.dismiss();

                                    // Maps
                                    Intent intent = new Intent(v.getContext(), MapsActivity.class);
                                    intent.putExtra("book_id", request.getBook().getId());
                                    v.getContext().startActivity(intent);

                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "REJECT",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    request.reject();
                                    Notification.deleteWhereRequestAndUser(request, request.getBook().getOwner());

                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

            });
        }
    }
}