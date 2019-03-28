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

import ca.ualberta.c301w19t14.onebook.models.Request;

public class BookRequestAdapter extends RecyclerView.Adapter<BookRequestAdapter.UserViewHolder> {

    public View view;
    public ArrayList<Request> RequestList;
    public Context mContext;

    /**
     *
     * @param context
     * @param RequestList

     */
    public BookRequestAdapter(Context context, ArrayList<Request> RequestList) {
        this.RequestList = RequestList;
        this.mContext = context;

    }

    /**
     *
     * @return bookList.size()
     */
    @Override
    public int getItemCount() {
        return RequestList.size();
    }

    /**
     *
     * @param
     * @param i
     */
    @Override
    public void onBindViewHolder(UserViewHolder userVh, int i) {

        Request request = RequestList.get(i);
        userVh.User.setText(request.getUser().getName());
        userVh.User.setText(request.getDate());


    }

    /**
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.book_request_list_item, viewGroup, false);

        return new UserViewHolder(itemView);
    }

    /**
     *
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {
        protected TextView User;
        protected  TextView Date;

        /**
         *
         * @param v
         */
        public UserViewHolder(View v) {
            super(v);

            User =  v.findViewById(R.id.user);
            Date = v.findViewById(R.id.date);

            //make the cards clickable
            view = v;
            view.setOnClickListener(new View.OnClickListener() {

                //create bundle to pass the current book to the view book page
                @Override public void onClick(View v){

                }
            });
        }
    }
}