package ca.ualberta.c301w19t14.onebook.util;

import android.provider.Settings;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.Request;
import ca.ualberta.c301w19t14.onebook.User;

public class GeneralUtil {

    public Globals globals;

    public ArrayList<Request> findOwnerRequests(){

        ArrayList<Request> owner_requests = new ArrayList<Request>();
        ArrayList<Request> all_requests = new ArrayList<Request>();
        globals = Globals.getInstance();
        all_requests = globals.requests.getAllRequests();

        //find requests relevant to the owner
        for (Request request : all_requests) {
            //Log.d("Requests", request.getOwneremail());
            if (request.getOwneremail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                owner_requests.add(request);
            }
        }

        return owner_requests;

    }

    public ArrayList<Book> findBorrowerBooks(){

        ArrayList<Book> all_books = new ArrayList<>();
        globals = Globals.getInstance();
        all_books = globals.books.getAllBooks();

        ArrayList<Book> borrower_books = new ArrayList<Book>();

        //see which books' borrowerID matches the given userID

        for (Book book : all_books){
            if (book.getBorrower().getEmail() == null){
                int temp = 1+1;
            }
            else {
                if (book.getBorrower().getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                    borrower_books.add(book);
                }
            }

        }

        return borrower_books;
    }

}
