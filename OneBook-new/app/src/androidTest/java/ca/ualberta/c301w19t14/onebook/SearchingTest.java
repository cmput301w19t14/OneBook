package ca.ualberta.c301w19t14.onebook;

import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Rule;

import java.util.Map;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onView;
//import static android.test.espresso..contrib;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

public class SearchingTest {



    @Rule
    public ActivityTestRule<SearchingActivity> activityRule =
            new ActivityTestRule<SearchingActivity>(SearchingActivity.class);

    public void SearchingTest()
    {
        onView(withId(R.id.search)).perform(typeText("te"));
        onView(withId(R.id.SearchButton)).perform(click());
        onView(withId(R.id.SearchRecycler)).check(matches(hasDescendant(withText("Some content"))));







    }


}
