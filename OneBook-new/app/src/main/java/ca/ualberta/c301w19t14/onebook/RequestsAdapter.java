package ca.ualberta.c301w19t14.onebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Handles the request process
 */
public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestsViewHolder> {

    public View view;
    private ArrayList<Request> requestList;
    public Context mContext;

    public RequestsAdapter(Context context, ArrayList<Request> requestList) {
        this.requestList = requestList;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    @Override
    public void onBindViewHolder(RequestsViewHolder mVh, int i) {
        Request request = requestList.get(i);

        mVh.book.setText("Book title: " + request.getBook().getTitle());
        mVh.user.setText("Requester: " + request.getUser().getName());
        mVh.request = request;
    }

    @Override
    public RequestsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.request_list_item, viewGroup, false);

        return new RequestsViewHolder(itemView, i);
    }

    public class RequestsViewHolder extends RecyclerView.ViewHolder {
        protected TextView book;
        protected TextView user;
        public Request request;

        RequestsViewHolder(View v, int i) {
            super(v);

            book =  (TextView) v.findViewById(R.id.title);
            user = (TextView)  v.findViewById(R.id.content);

            //make the cards clickable
            view = v;
            view.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v){
                    AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                    alertDialog.setTitle("Accept/Reject");
                    alertDialog.setMessage("What would you like to do?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ACCEPT",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    request.setStatus("Accepted");
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Requests");
                                    myRef.child(request.getId()).setValue(request);

                                    Book book = request.getBook();
                                    book.setStatus("Borrowed");
                                    book.setBorrower(request.getUser());
                                    myRef = database.getReference("Books");
                                    myRef.child(request.getBook().getId()).setValue(request.getBook());

                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "REJECT",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    request.setStatus("Rejected");
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Requests");
                                    myRef.child(request.getId()).setValue(request);

                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
        }
    }
}