package ca.ualberta.c301w19t14.onebook.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.activities.MessageActivity;
import ca.ualberta.c301w19t14.onebook.models.Chat;
import ca.ualberta.c301w19t14.onebook.models.User;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * This class implements the adapter for displaying the users in the MessagingUserFragment.
 * Each user will have their last message displayed if the current user hasn't responded back.
 * @author jandaile CMPUT 301 team 14
 * @version 1.0
 * @see ca.ualberta.c301w19t14.onebook.fragments.MessagingUsersFragment
 * @since 2019-03-29
 */
public class MessagingUserAdapter extends RecyclerView.Adapter<MessagingUserAdapter.ViewHolder>{

    private Context mContext;
    private List<User> mUsers;

    private String theLastMessage;

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public MessagingUserAdapter(Context mContext, List<User> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;

    }

    /**
     *
     * @param viewGroup: the viewlayout where the view belongs to
     * @param i: position in the view layout
     * @return a view of the messages
     */
    @NonNull
    @Override
    public MessagingUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.users_item, viewGroup, false);
        return new MessagingUserAdapter.ViewHolder(view);
    }

    /**
     *
     * @param viewHolder: the view being looked at
     * @param i: position in the user list
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final User user = mUsers.get(i);
        viewHolder.username.setText(user.getName());

        lastMessage(user.getUid(), viewHolder.lastMessage);

        StorageReference ref = storage.getReference("Profile pictures/"+user.getUid()+"/profile.png");
        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()) {
                    Glide.with(mContext).load(task.getResult()).placeholder(R.drawable.book64).into(viewHolder.profilePic);
                }
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID",user.getUid());
                bundle.putString("EMAIL",user.getEmail());
                bundle.putString("NAME",user.getName());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    /**
     *
     * @return the total number of users for testing
     */
    @Override
    public int getItemCount() {

        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private TextView lastMessage;
        private CircleImageView profilePic;


        public ViewHolder(View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePicture);
            username = itemView.findViewById(R.id.user);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }
    }

    /**
     * Grabs the last message sent by the receiver.
     * @param userID: The user from the database.
     * @param lastMessage: The last message from this user to the current user.
     */
    private void lastMessage(final String userID, final TextView lastMessage) {
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userID)){
                        theLastMessage = chat.getMessage();
                    }
                    if (chat.getReceiver().equals(userID) && chat.getSender().equals(userID)){
                        theLastMessage = chat.getMessage();
                    }

                    switch (theLastMessage){

                        case "default":
                            lastMessage.setText("No new messages");
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
