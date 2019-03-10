package ca.ualberta.c301w19t14.onebook.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.Book;
import ca.ualberta.c301w19t14.onebook.User;

public class FirebaseUtil {
    FirebaseDatabase db;
    DatabaseReference ref;
    DataSnapshot data;

    public FirebaseUtil(String ref) {
        this.db = FirebaseDatabase.getInstance();
        this.ref = this.db.getReference(ref);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data = dataSnapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public DataSnapshot getData() {
        return this.data;
    }
}
