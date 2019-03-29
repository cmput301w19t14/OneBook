package ca.ualberta.c301w19t14.onebook.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.models.Request;

import static java.lang.Thread.sleep;

public class FirebaseUtil {
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private DataSnapshot data;
    public boolean data_loaded;

    public FirebaseUtil(String table) {
        this.db = FirebaseDatabase.getInstance();
        this.ref = this.db.getReference(table);
        this.data_loaded = false;

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data = dataSnapshot;
                data_loaded = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public ArrayList<Request> getAllRequests(){

        ArrayList<Request> requests = new ArrayList<Request>();

        for (DataSnapshot ds : data.getChildren()) {
            Request r = ds.getValue(Request.class);
            requests.add(r);
        }

        return requests;
    }

    public ArrayList<Book> getAllBooks(){

        ArrayList<Book> books = new ArrayList<Book>();

        //iterate through books, loading in each one
        for (DataSnapshot ds : data.getChildren()){

            //finally, create the book
            Book b = ds.getValue(Book.class);

            // TODO: Override status.

            books.add(b);
        }

        long i = data.getChildrenCount();

        //DEBUG
        for (int j = 0; j < i; j++){
            Log.d("Onebook", "Children: "+ books.get(j).getAuthor());
        }


        return books;

    }

    public boolean isNull(){
        if (data.getChildren() == null) {
            return true;
        }
        else{
            return false;
        }
    }

    public DataSnapshot getData() {

        return this.data;
    }
}
