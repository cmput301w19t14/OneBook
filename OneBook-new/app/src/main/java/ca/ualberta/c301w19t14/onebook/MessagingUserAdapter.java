package ca.ualberta.c301w19t14.onebook;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MessagingUserAdapter extends RecyclerView.Adapter<MessagingUserAdapter.ViewHolder>{

    private Context mContext;
    private List<User> mUsers;

    String theLastMessage;

    public MessagingUserAdapter(Context mContext, List<User> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public MessagingUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.users_item, viewGroup, false);
        return new MessagingUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final User user = mUsers.get(i);
        viewHolder.username.setText(user.getName());

        lastMessage(user.getUid(), viewHolder.lastMessage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userID", user.getUid());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        private TextView lastMessage;


        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.user);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }
    }

    private void lastMessage(final String userID, final TextView lastMessage) {
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userID) ||
                            chat.getReceiver().equals(userID) && chat.getSender().equals(userID)){

                        theLastMessage = chat.getMessage();
                    }

                    switch (theLastMessage){

                        case "default":
                            lastMessage.setText("No message");
                            break;

                        default:
                            lastMessage.setText(theLastMessage);
                            break;

                    }

                    theLastMessage = "default";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
