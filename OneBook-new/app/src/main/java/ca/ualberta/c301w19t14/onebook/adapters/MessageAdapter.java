package ca.ualberta.c301w19t14.onebook.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.Chat;

/**
 * This class is implements the adapter for viewing the chat messages.
 * The messages organized to show which messages are from the sender vs
 * the receiver from this class.
 * @author jandaile CMPUT 301 team 14
 * @version 1.0
 * @see ca.ualberta.c301w19t14.onebook.activities.MessageActivity
 * @since 2019-03-29
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Chat> mChat;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context mContext, List<Chat> mChat){
        this.mChat = mChat;
        this.mContext = mContext;

    }

    /**
     * Initializes the view based on the sender and receiver messages.
     * @param viewGroup: which viewlayout in view will belongs in
     * @param viewType: based on who the user is. Changes the side the messages are on.
     * @return
     */
    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    /**
     * Attaches the messages to the view.
     * @param viewHolder: view for the messages
     * @param i: chat message index in the list of messages
     */
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {

        Chat chat = mChat.get(i);

        viewHolder.showMessage.setText(chat.getMessage());

    }

    /**
     *
     * @return the total chat size for any tests or checks.
     */
    @Override
    public int getItemCount() {

        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView showMessage;


        public ViewHolder(View itemView) {
            super(itemView);

            showMessage = itemView.findViewById(R.id.showMessage);
        }
    }

    /**
     * Determines who the message is from.
     * @param i: message position in the chat list.
     * @return The view type for adapter use.
     */
    @Override
    public int getItemViewType(int i) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(i).getSender().equals(firebaseUser.getUid())){
            return  MSG_TYPE_RIGHT;
        }
        else
            return MSG_TYPE_LEFT;
    }
}

