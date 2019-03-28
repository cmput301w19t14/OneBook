package ca.ualberta.c301w19t14.onebook.fragements;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.adapters.MessagingUserAdapter;
import ca.ualberta.c301w19t14.onebook.R;
import ca.ualberta.c301w19t14.onebook.models.User;
import ca.ualberta.c301w19t14.onebook.util.GeneralUtil;


public class MessagingUsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private MessagingUserAdapter messagingUserAdapter;
    GeneralUtil util;
    Globals globals;
    public ArrayList<User> users = new ArrayList<User>();

    EditText searchUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_messaging_users, container, false);

        //get globals
        globals = Globals.getInstance();
        util = new GeneralUtil();

        recyclerView = myView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        users = new ArrayList<>();
        readUsers();

        searchUsers = myView.findViewById(R.id.searchUsers);
        searchUsers.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchingUsers(s.toString());
                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return myView;
    }

    private void searchingUsers(String s) {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("name")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    assert user != null;
                    assert firebaseUser != null;
                    if (!(user.getName().equals(firebaseUser.getDisplayName()))){
                        users.add(user);
                    }
                }

                messagingUserAdapter = new MessagingUserAdapter(getContext(), users);
                recyclerView.setAdapter(messagingUserAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readUsers(){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (searchUsers.getText().toString().equals("")) {
                    users.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        if (!(user.getUid().equals(firebaseUser.getUid()))) {
                            users.add(user);
                        }
                    }
                    messagingUserAdapter = new MessagingUserAdapter(getContext(), users);
                    recyclerView.setAdapter(messagingUserAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
