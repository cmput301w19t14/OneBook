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
import com.google.firebase.database.DataSnapshot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

//import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import ca.ualberta.c301w19t14.onebook.activities.MainActivity;
import ca.ualberta.c301w19t14.onebook.fragments.LendingFragment;
import ca.ualberta.c301w19t14.onebook.models.Book;

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
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


/**
 * This tests checks to see if a user can add and delete a book from his/her owned books
 * @author CMPUT301 Team14
 */

public class AddDeleteBookTest {

    //Variables needed for login
    public boolean complete;
    public String email = "UITest@gmail.com";
    public String password = "test123";

    //Variables needed for functionality
    private Globals globals;
    private int resID = R.id.bookList;

    //input details for creating the book
    public String booktitle = "The Art of the Deal";
    public String bookauthor = "Orange Man";
    public String bookdescription = "How to business 101";
    public String bookisbn = "15297723";

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

        StartLendingTransaction();

    }

    @Test
    public void AddDeleteTest()
    {

        onView(withId(R.id.newBook)).perform(click());

        //at this point, we are at the add book activity. Now we need to fill in the details
        //of our new book.
        onView(withId(R.id.title)).perform(typeText(booktitle));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.author)).perform(typeText(bookauthor));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.isbn)).perform(typeText(bookisbn));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.description)).perform(typeText(bookdescription));
        //once the details are filled out, add the book
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.add)).perform(click());

        //back at the main screen, open lending fragment (might already be open, but commit
        //fragment transaction again for safe measure
        StartLendingTransaction();

        //click on the book you just created
        onView(new RecyclerViewMatcher(this.resID)
                .atPositionOnView(1, R.id.bookTitle))
                .perform(click());

        //Click "Delete"
        onView(withId(R.id.deleteIcon)).perform(click());
        onView(withText("YES")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //restart lending transaction
        StartLendingTransaction();
    }

    public void StartLendingTransaction(){
        ArrayList<Book> book = new ArrayList<Book>();

        for(DataSnapshot snapshot : globals.books.getData().getChildren()) {
            Book b = snapshot.getValue(Book.class);

            if(b.getOwner().getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
            {
                book.add(b);
            }
        }

        LendingFragment mylendingfragment = new LendingFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mylendingfragment);
        fragmentTransaction.commit();

    }



}
