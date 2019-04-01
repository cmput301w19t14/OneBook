package ca.ualberta.c301w19t14.onebook;

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

import androidx.test.rule.ActivityTestRule;
import ca.ualberta.c301w19t14.onebook.activities.MainActivity;
import ca.ualberta.c301w19t14.onebook.fragments.MyProfileFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * This test checks to see if the correct details are displayed on the MyAccount page after the
 * user logs in
 * @author CMPUT301 Team14
 */

public class MyAccountTest {

    //Variables used for logging in
    public boolean complete;
    public String email = "UITest@gmail.com";
    public String email2 = "UITest2@gmail.com";
    public String password = "test123";
    public String password2 = "test456";

    //Variables used for checking
    public String check_name;
    public String actual_name = "John Smith";
    public String actual_name2 = "Mary Jane";
    public String DUSTIN_EMAIL = "dustinmcrorie@gmail.com";
    public String DUSTIN_PASS = "hunter2";

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class, false, false);

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
        Intent i = new Intent();
        activityRule.launchActivity(i);

        MyProfileFragment myProfileFragment = new MyProfileFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myProfileFragment);
        fragmentTransaction.commit();
    }

    @Test
    public void CheckUserDetails(){
        //ensure that the right name and email are displayed on the profile page
        onView(withId(R.id.Name)).check(matches(withText(actual_name)));
        onView(withId(R.id.email)).check(matches(withText(email.toLowerCase())));
    }

}