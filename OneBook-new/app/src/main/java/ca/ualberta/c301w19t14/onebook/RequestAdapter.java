package ca.ualberta.c301w19t14.onebook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.UserViewHolder> {

    public View view;
    private ArrayList<Request> RequestList;
    public Context mContext;
    public Boolean mode;

    /**
     *
     * @param context
     * @param RequestList
     * @param mode
     */
    public RequestAdapter(Context context, ArrayList<Request> RequestList, Boolean mode) {
        this.RequestList = RequestList;
        this.mContext = context;
        this.mode = mode;
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
                inflate(R.layout.book_list_item, viewGroup, false);

        return new UserViewHolder(itemView);
    }

    /**
     *
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {


        /**
         *
         * @param v
         */
        public UserViewHolder(View v) {
            super(v);

            //vImage = v.findViewById(R.id.bookImage);

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
