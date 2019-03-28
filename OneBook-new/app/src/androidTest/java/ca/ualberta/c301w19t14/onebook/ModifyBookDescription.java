package ca.ualberta.c301w19t14.onebook;

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

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import ca.ualberta.c301w19t14.onebook.activities.MainActivity;
import ca.ualberta.c301w19t14.onebook.fragements.LendingFragment;
import ca.ualberta.c301w19t14.onebook.models.Book;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

public class ModifyBookDescription {

    public boolean complete;
    public String email = "UITest3@gmail.com";
    public String name = "You Eye";
    public String password = "test789";
    private String description_prefix = "Description: ";
    public String before_description = "This is a book about the end of the world!";
    private String after_description = "This book is about mayhem and Armageddon.";

    private Globals globals;
    private int resID = R.id.recyclerView;

    //input details
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
    public void ModifyDescriptionTest(){

        //click on the book (should only be one)
        onView(new RecyclerViewMatcher(this.resID)
                .atPositionOnView(0, R.id.bookTitle))
                .perform(click());

        //check to see if the before description matches
        onView(withId(R.id.bookDescription))
                .check(matches(withText(description_prefix + before_description)));

        //click into the edit button
        onView(withId(R.id.editBookButton)).perform(click());

        //now we're in the edit book page. Change the description
        onView(withId(R.id.editBookDescription)).perform(replaceText(""));
        onView(withId(R.id.editBookDescription)).perform(typeText(after_description));

        //Hide the keyboard
        Espresso.closeSoftKeyboard();

        //so we've typed in the new description. Click save
        onView(withId(R.id.saveBookButton)).perform(click());

        //press the back button
        Espresso.pressBack();

        //click on the book again
        onView(new RecyclerViewMatcher(this.resID)
                .atPositionOnView(0, R.id.bookTitle))
                .perform(click());

        //check to see if the before description matches
        onView(withId(R.id.bookDescription))
                .check(matches(withText(description_prefix + after_description)));

        //click into the edit button
        onView(withId(R.id.editBookButton)).perform(click());

        //now we're in the edit book page. Change the description
        //onView(withId(R.id.editBookDescription)).perform(typeText(after_description));
        //TEMP
        onView(withId(R.id.editBookDescription)).perform(replaceText(""));
        onView(withId(R.id.editBookDescription)).perform(typeText(before_description));

        //Hide the keyboard
        Espresso.closeSoftKeyboard();

        //so we've typed in the new description. Click save
        onView(withId(R.id.saveBookButton)).perform(click());

        //as a final precaution, check to see that the description is back to what it was
        //before
        onView(withId(R.id.bookDescription))
                .check(matches(withText(description_prefix + before_description)));

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

        LendingFragment mylendingfragment = new LendingFragment(book);
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mylendingfragment);
        fragmentTransaction.commit();

    }
}
