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

    public ArrayList<Book> findBorrowerBooks(){

        ArrayList<Book> all_books = new ArrayList<>();
        globals = Globals.getInstance();
        all_books = globals.books.getAllBooks();

        ArrayList<Book> borrower_books = new ArrayList<Book>();

        //see which books' borrowerID matches the given userID

        for (Book book : all_books){
            if (book.getBorrower() == null){
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
