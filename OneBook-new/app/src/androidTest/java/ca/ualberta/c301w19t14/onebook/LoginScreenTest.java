package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.auth.ui.email.EmailLinkFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.TypeTextAction;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.AllOf.allOf;

public class LoginScreenTest {

    public boolean complete;
    public Globals globals;
    public String email;
    public String password;
    public String check_name;
    public String actual_name;

    @Rule
    public ActivityTestRule<UserLoginActivity> activityRule =
            new ActivityTestRule<UserLoginActivity>(UserLoginActivity.class, false, false);

    //REFERENCe
    //onView(withId(R.layout.fui_email_link_sign_in_layout)

    @Test
    public void CheckLoginScreen() throws InterruptedException {

        email = "UITest@gmail.com";
        password = "test123";
        actual_name = "John Smith";

        Intent i = new Intent();
        activityRule.launchActivity(i);

        Thread.sleep(1500);

        //enter the email (username) and click next
        onView(withId(R.id.email)).perform(typeText(email));
        onView(withId(R.id.button_next)).perform(click());

        Thread.sleep(1500);

        //enter the password and click sign in
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.button_done)).perform(click());

        Thread.sleep(4500);

        //open the navigation menu so that the "my account" item can be clicked
        onView(withContentDescription("Open navigation drawer"))
                .perform(click());

        //click on the my account item in the navigation drawer
        onView(nthChildOf(withId(R.id.design_navigation_view), 4)).perform(click());

        //now we should be at the my account fragment.
        //check to see that "John Smith" is the name on display
        onView(withId(R.id.Name)).check(matches(withText("Name: " + actual_name)));


        //InfiniteLoop();

    }

    //https://stackoverflow.com/questions/24748303/selecting-child-view-at-index-using-espresso
    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with "+childPosition+" child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition).equals(view);
            }
        };
    }

}
