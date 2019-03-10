package ca.ualberta.c301w19t14.onebook.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUtil {
    FirebaseDatabase db;
    DatabaseReference ref;
    DataSnapshot data;

    public FirebaseUtil() {
        this.db = FirebaseDatabase.getInstance();
        this.ref = this.db.getReference();

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

    public DataSnapshot getDataSnapshop() {
        return this.data;
    }
}
