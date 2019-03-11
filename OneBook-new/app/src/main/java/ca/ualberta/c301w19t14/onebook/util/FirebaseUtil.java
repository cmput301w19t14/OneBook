package ca.ualberta.c301w19t14.onebook.util;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.AbsListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.Book;
import ca.ualberta.c301w19t14.onebook.Request;
import ca.ualberta.c301w19t14.onebook.User;

import static java.lang.Thread.sleep;

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

            //load in raw data
            String title = (String) ds.child("title").getValue();
            long ISBN = (long) ds.child("isbn").getValue();
            String status = (String) ds.child("status").getValue();
            String author = (String) ds.child("author").getValue();
            String oemail = (String) ds.child("owner").child("email").getValue();

            String oname = (String) ds.child("owner").child("name").getValue();

            String ouid = (String) ds.child("owner").child("uid").getValue();

            //load in user and borrower
            User owner = new User(ouid, oname, oemail);

            //only load in borrower if the book is not available
            User borrower;
            if (!status.equals("Available") || (!status.equals("Requested"))) {
                String bemail = (String) ds.child("borrower").child("email").getValue();
                String bname = (String) ds.child("borrower").child("name").getValue();
                String buid = (String) ds.child("borrower").child("uid").getValue();
                borrower = new User(buid, bname, bemail);
            }
            else
                borrower = null;


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
