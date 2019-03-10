package ca.ualberta.c301w19t14.onebook.util;

import java.util.ArrayList;

import ca.ualberta.c301w19t14.onebook.Book;
import ca.ualberta.c301w19t14.onebook.User;

public class GeneralUtil {

    public ArrayList<Book> findBorrowerBooks(ArrayList<Book> books, User user){

        ArrayList<Book> borrower_books = new ArrayList<Book>();

        //see which books' borrowerID matches the given userID
        for (Book book : books){
            if (book.getBorrower().getUid() == user.getUid()){
                borrower_books.add(book);
            }
        }

        return borrower_books;
    }

}
