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
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private DataSnapshot data;

    public FirebaseUtil(String table) {
        this.db = FirebaseDatabase.getInstance();
        this.ref = this.db.getReference(table);

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

    public ArrayList<Book> getAllBooks(){

        ArrayList<Book> books = new ArrayList<Book>();

        //iterate through books, loading in each one
        for (DataSnapshot ds : data.getChildren()){

            //load in raw data
            String title = (String) ds.child("title").getValue();
            long ISBN = (long) ds.child("ISBN").getValue();
            String status = (String) ds.child("status").getValue();
            String author = (String) ds.child("author").getValue();
            String bemail = (String) ds.child("borrower").child("email").getValue();
            String oemail = (String) ds.child("owner").child("email").getValue();
            String bname = (String) ds.child("borrower").child("name").getValue();
            String oname = (String) ds.child("owner").child("name").getValue();
            String buid = (String) ds.child("borrower").child("uid").getValue();
            String ouid = (String) ds.child("owner").child("uid").getValue();

            //load in user and borrower
            User owner = new User(ouid, oname, oemail);
            User borrower = new User(buid, bname, bemail);

            //finally, create the book
            Book b = new Book(ISBN, title, author, null, owner, borrower, null,
                    status);
            books.add(b);
        }

        long i = data.getChildrenCount();

        //DEBUG
        for (int j = 0; j < i; j++){
            Log.d("Onebook", "Children: "+ books.get(j).getAuthor());
        }


        return books;

    }

    public DataSnapshot getData() {
        return this.data;
    }
}
