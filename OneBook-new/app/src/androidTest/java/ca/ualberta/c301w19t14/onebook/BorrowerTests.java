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

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.rule.ActivityTestRule;
import ca.ualberta.c301w19t14.onebook.activities.MainActivity;
import ca.ualberta.c301w19t14.onebook.fragments.BorrowingFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * This tests checks to see if the book a user is currently borrowing appears in his/her list of
 * borrowed books
 * @author CMPUT301 Team14
 */

public class BorrowerTests {

    //login details
    public boolean complete;
    public String email = "UITest@gmail.com";
    public String email2 = "UITest2@gmail.com";
    public String password = "test123";
    public String password2 = "test456";
    public String check_name;
    public String actual_name = "John Smith";
    public String actual_name2 = "Mary Jane";
    public String DUSTIN_EMAIL = "dustinmcrorie@gmail.com";
    public String DUSTIN_PASS = "hunter2";

    //variables needed for functionality
    private int resID = R.id.bookList;
    private RecyclerView mRecyclerView;

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

        BorrowingFragment borrowingFragment = new BorrowingFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, borrowingFragment);
        fragmentTransaction.commit();
    }


    @Test
    public void CheckBookDetails() throws InterruptedException{

        Thread.sleep(1500);


        //Check to see if the title of the book is what it should be
        onView(new RecyclerViewMatcher(this.resID)
                        .atPositionOnView(1, R.id.bookTitle))
                .check(matches(withText("Polywogs and jiblegarble")));

        //check to see if the book is borrowed (it should be)
        onView(new RecyclerViewMatcher(this.resID)
                .atPositionOnView(1, R.id.bookStatus))
                .check(matches(withText("BORROWED")));

    }

}
