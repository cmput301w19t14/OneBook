package ca.ualberta.c301w19t14.onebook.util;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import ca.ualberta.c301w19t14.onebook.models.Book;
import ca.ualberta.c301w19t14.onebook.Globals;
import ca.ualberta.c301w19t14.onebook.models.Request;

public class DykUtil {

    private ArrayList<String> dyk = new ArrayList<>();

    DykUtil() {
        dyk.add("OneBook has a custom, in house, messaging system. You can find it in the main menu, just above logout. Give it a try!");
        dyk.add("You can view book details and even start the borrow/return process from most pages. Look for the camera icon, on the top right, to quickly scan an ISBN.");
        dyk.add("OneBook features a waiting list system. If a book is currently borrowed, you can be put on the waitlist to get it as quick as possible.");
        dyk.add("Looking to personalize your account a little more? Add an account photo, and let fellow users put a face to your name.");
        dyk.add("You can search for books by title, author, description and status.");
    }

    public String getDyk() {
        int r = new Random().nextInt() % (dyk.size() - 1);

        return dyk.get(r);
    }
}
