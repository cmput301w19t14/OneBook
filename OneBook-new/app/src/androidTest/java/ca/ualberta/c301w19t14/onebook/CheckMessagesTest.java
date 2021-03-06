package ca.ualberta.c301w19t14.onebook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import ca.ualberta.c301w19t14.onebook.activities.MainActivity;
import ca.ualberta.c301w19t14.onebook.fragments.MessagesFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * This tests checks to see if the user can click into his/her messages tab, then open a conversation
 * between himself/herself and another user
 * @author CMPUT301 Team14
 */

public class CheckMessagesTest {

    //needed for functionality
    private Globals globals;
    private int resID = R.id.recyclerView;

    //Log in variables
    private boolean complete = false;
    public String owner_email = "OwnerTim@gmail.com";
    public String owner_name = "Tim Watto";
    public String owner_pass = "owner1";
    public String requester_email = "RequesterMike@gmail.com";
    public String requester_name = "Mike Scott";
    public String requester_pass = "request";


    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class, false, false);

    @Before
    public void init(){
        FirebaseAuth.getInstance().signOut();

        Task<AuthResult> result = FirebaseAuth.getInstance().signInWithEmailAndPassword(
                owner_email, owner_pass);

        Log.d("MYDEBUG", "executing listener");
        result.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //check_name = task.getResult().getUser().getDisplayName();
                complete = true;
            }
        });


        //wait until successfuly logged in
        while (true) {

            if (complete){

                break;

            }
        }

        globals = Globals.getInstance();
        globals.initFirebaseUtil();

        Intent i = new Intent();
        activityRule.launchActivity(i);

        StartMessagesTransaction();
    }

    @Test
    public void CheckMessage() throws InterruptedException{

        //StartChatTransaction();

        ClickViewPagerChat();


        Thread.sleep(1500);
        //Check to see if the title of the book is what it should be
        onView(nthChildOf(nthChildOf(nthChildOf(withId(R.id.viewPager), 1), 1), 0))
                .perform(click());

    }

    public void StartMessagesTransaction(){
        MessagesFragment messagesFragment = new MessagesFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, messagesFragment);
        fragmentTransaction.commit();
    }

    public void ClickViewPagerChat(){
        onView(withContentDescription("Users")).perform(click());
    }

    public static Matcher<View> firstChildOf(final Matcher<View> parentMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with first child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {

                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }
                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(0).equals(view);

            }
        };
    }

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
