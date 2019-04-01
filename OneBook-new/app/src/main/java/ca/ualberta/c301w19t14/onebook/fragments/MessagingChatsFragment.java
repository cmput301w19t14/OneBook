package ca.ualberta.c301w19t14.onebook.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.c301w19t14.onebook.models.MessagingChatsList;
import ca.ualberta.c301w19t14.onebook.adapters.MessagingUserAdapter;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.User;

/**
 * This class implements the Chatlist functionality of the Messaging part of the app.
 * From this class, messages that exist between two users are held then displayed.
 * @author jandaile CMPUT 301 team 14
 * @version 1.0
 * @see MessagesFragment
 * @since 2019-03-29
 */
public class MessagingChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private MessagingUserAdapter messagingUserAdapter;
    private List<User> Users;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private List<MessagingChatsList> usersList;

    /**
     * Initializes the view.
     * @param inflater: view inflater for the layout used
     * @param container: container the view is held in
     * @param savedInstanceState: last possible state
     * @return the view for the chat fragment
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messaging_chats, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    MessagingChatsList messagingChatsList = snapshot.getValue(MessagingChatsList.class);
                    usersList.add(messagingChatsList);
                }
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    /**
     * The method checks for users that already have chats so that they'll be displayed here.
     */
    private void chatList() {
        Users = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for (MessagingChatsList messagingChatsList : usersList){
                        if(user.getUid().equals(messagingChatsList.getId())){
                            Users.add(user);
                        }
                    }
                }

                messagingUserAdapter = new MessagingUserAdapter(getContext(), Users);
                recyclerView.setAdapter(messagingUserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
