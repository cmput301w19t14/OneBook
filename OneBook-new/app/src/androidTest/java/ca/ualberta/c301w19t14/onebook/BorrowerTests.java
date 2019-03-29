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
import ca.ualberta.c301w19t14.onebook.fragements.BorrowingFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class BorrowerTests {

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
    private int resID = R.id.borrow_recycler;
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
    public void CheckBookDetails(){

        //Check to see if the title of the book is what it should be
        onView(new RecyclerViewMatcher(this.resID)
                        .atPositionOnView(0, R.id.bookTitle))
                .check(matches(withText("UI Test Book 1")));

        //check to see if the book is borrowed (it should be)
        onView(new RecyclerViewMatcher(this.resID)
                .atPositionOnView(0, R.id.bookStatus))
                .check(matches(withText("BORROWED")));

    }

}
