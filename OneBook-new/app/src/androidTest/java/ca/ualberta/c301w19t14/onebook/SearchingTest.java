package ca.ualberta.c301w19t14.onebook;

//import androidx.test.espresso.contrib.RecyclerViewActions;
//import android.support.v7.widget.RecyclerView;

//import androidx.test.espresso.contrib.RecyclerViewActions;

import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

//import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

//import static androidx.test.espresso.Espresso.onData;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onView;
//import static android.test.espresso..contrib;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withId;



public class SearchingTest {



    @Rule
    public ActivityTestRule<SearchingActivity> activityRule =
            new ActivityTestRule<SearchingActivity>(SearchingActivity.class);
    @Test
    public void SearchingTest()
    {
        onView(withId(R.id.search)).perform(typeText("te"));
        onView(withId(R.id.SearchButton)).perform(click());
        //onView(withId(R.id.SearchRecycler)).perform(RecyclerViewActions.<VH>actionOnItemAtPosition(ITEM_BELOW_THE_));
                //.check(matches(hasDescendant(withText("Test2"))));







    }


}
