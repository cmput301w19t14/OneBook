package ca.ualberta.c301w19t14.onebook;


import android.content.Intent;
import android.os.Bundle;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class ViewRequestableTest {

    @Rule
    public ActivityTestRule<ViewRequestableActivity> activityRule =
            new ActivityTestRule<ViewRequestableActivity>(ViewRequestableActivity.class, false, false);


    @Test
    public void testViewBook(){

        //init variables
        long ISBN = 940123748L;
        String description = "Happy book!";
        String status = "Available";
        String title = "Fun Book";
        String author = "Winston Churchill";
        String owner = "adam@gmail.com";
        String name = "Adam West";


        Bundle bundle = new Bundle();
        bundle.putLong("ISBN",ISBN);
        bundle.putString("DESCRIPTION",description);
        bundle.putString("STATUS",status);
        bundle.putString("TITLE",title);
        bundle.putString("AUTHOR",author);
        bundle.putString("OWNER",owner);
        bundle.putString("NAME",name);

        Intent i = new Intent();
        i.putExtras(bundle);

        activityRule.launchActivity(i);

        onView(withId(R.id.bookTitle)).check(matches(withText(title)));
        onView(withId(R.id.bookauthor)).check(matches(withText(author)));
        onView(withId(R.id.bookIsbn)).check(matches(withText(String.valueOf(ISBN))));
        onView(withId(R.id.bookOwner)).check(matches(withText(name)));
        onView(withId(R.id.bookDescription)).check(matches(withText(description)));
        onView(withId(R.id.bookStatus)).check(matches(withText(status)));

    }

}