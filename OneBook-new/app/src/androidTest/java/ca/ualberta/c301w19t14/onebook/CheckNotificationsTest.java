package ca.ualberta.c301w19t14.onebook;

//import androidx.test.espresso.contrib.RecyclerViewActions;
//import android.support.v7.widget.RecyclerView;

//import androidx.test.espresso.contrib.RecyclerViewActions;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

//import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import ca.ualberta.c301w19t14.onebook.activities.MainActivity;
import ca.ualberta.c301w19t14.onebook.activities.SearchingActivity;
import ca.ualberta.c301w19t14.onebook.fragments.BorrowingFragment;
import ca.ualberta.c301w19t14.onebook.fragments.NotificationFragment;

//import static androidx.test.espresso.Espresso.onData;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onView;
//import static android.test.espresso..contrib;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * This test checks to see if the notifications that get displayed are working correctly
 * @author CMPUT301 Team14
 */

public class CheckNotificationsTest {

    //Login variables
    public boolean complete;
    public String email = "UITest4@gmail.com";
    public String password = "testing";

    //Variables used for functionality
    public int resID = R.id.requestList;
    private Globals globals;
    private Methods methods = new Methods();

    //Variables for validation
    private String request_content = "Dustin McRorie has requested Making News. Click to approve or reject.";

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void init(){
        FirebaseAuth.getInstance().signOut();
        boolean UserLoaded = false;

        Task<AuthResult> result = FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password);

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

        //wait for books to be loaded in
        globals = Globals.getInstance();
        globals.initFirebaseUtil();

        //wait for globals to load from firebase
        while(true){
            if (Globals.getInstance().books.data_loaded){
                break;
            }
        }

        Intent i = new Intent();
        activityRule.launchActivity(i);
    }

    @Test
    public void notificationsTest()
    {
        startNotificationsFragment();

        //Check to see if the content of the notification has the correct information
        onView(new RecyclerViewMatcher(this.resID)
                .atPositionOnView(0, R.id.content))
                .check(matches(withText(request_content)));

    }

    public void startNotificationsFragment(){
        NotificationFragment notificationFragment = new NotificationFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, notificationFragment);
        fragmentTransaction.commit();
    }

}
