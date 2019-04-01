package ca.ualberta.c301w19t14.onebook.util;

import java.util.ArrayList;
import java.util.Random;

public class DykUtil {

    private ArrayList<String> dyk = new ArrayList<>();

    public DykUtil() {
        dyk.add("OneBook has a custom, in house, messaging system. You can find it in the main menu, just above logout. Give it a try!");
        dyk.add("You can view book details and even start the borrow/return process from most pages. Look for the camera icon, on the top right, to quickly scan an ISBN.");
        dyk.add("OneBook features a waiting list system. If a book is currently borrowed, you can be put on the waitlist to get it as quick as possible.");
        dyk.add("Looking to personalize your account a little more? Add an account photo, and let fellow users put a face to your name.");
        dyk.add("You can search for books by title, author, description and status.");
    }

    public String getDyk() {
        int r = new Random().nextInt() % (dyk.size() - 1);
        if(r < 0) {
            r = r * -1;
        }

        return dyk.get(r);
    }
}
