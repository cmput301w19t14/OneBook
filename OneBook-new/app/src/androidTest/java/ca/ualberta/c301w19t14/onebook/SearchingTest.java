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
import ca.ualberta.c301w19t14.onebook.activities.SearchingActivity;

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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


public class SearchingTest {

    public int resID = R.id.bookList;

    public boolean complete;
    public String email = "UITest@gmail.com";
    public String email2 = "UITest2@gmail.com";
    public String password = "test123";

    private Globals globals;

    @Rule
    public ActivityTestRule<SearchingActivity> activityRule =
            new ActivityTestRule<SearchingActivity>(SearchingActivity.class, false, false);

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
    public void SearchingTest()
    {
        onView(withId(R.id.search)).perform(click());
        typeText("War of")
        //onView(withId(R.id.SearchButton)).perform(click());

        //DEBUG - delete later
        Methods methods = new Methods();
        methods.InfiniteLoop();

        //Check to see if the title of the book is what it should be
        onView(new RecyclerViewMatcher(this.resID)
                .atPositionOnView(0, R.id.bookTitle))
                .check(matches(withText("test2")));

        //onView(withId(R.id.SearchRecycler)).perform(RecyclerViewActions.<VH>actionOnItemAtPosition(ITEM_BELOW_THE_));
                //.check(matches(hasDescendant(withText("Test2"))));







    }


}
