package ca.ualberta.c301w19t14.onebook;


import android.content.Intent;
import android.os.Bundle;
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
import ca.ualberta.c301w19t14.onebook.activities.ViewBookActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class ViewBookTest {

    public Globals globals;
    public boolean complete;
    public String email = "UITest@gmail.com";
    public String email2 = "UITest2@gmail.com";
    public String password = "test123";

    @Rule
    public ActivityTestRule<ViewBookActivity> activityRule =
            new ActivityTestRule<ViewBookActivity>(ViewBookActivity.class, false, false);

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
        globals = Globals.getInstance();
        globals.initFirebaseUtil();

        //wait for globals to load from firebase
        while(true){
            if (Globals.getInstance().books.data_loaded){
                break;
            }
        }
    }

    @Test
    public void testViewBook(){

        //init variables
        long ISBN = 3789527L;
        String description = "This is a book about the end of the world!";
        String status = "Borrowed";
        String title = "Polywogs and jiblegarble";
        String author = "Crazy Person";
        String owner = "adam@gmail.com";
        String name = "You Eye";
        String id = "-LaTT1EdrC9h1oCdxyUT";


        Bundle bundle = new Bundle();
        bundle.putString("id", id);

        Intent i = new Intent();
        i.putExtras(bundle);

        activityRule.launchActivity(i);

        onView(withId(R.id.title)).check(matches(withText(title)));
        onView(withId(R.id.author)).check(matches(withText(author)));
        onView(withId(R.id.isbn))
                .check(matches(withText(String.valueOf(ISBN))));
        onView(withId(R.id.owner)).check(matches(withText(name)));
        onView(withId(R.id.description)).check(matches(withText(description)));
        onView(withId(R.id.status)).check(matches(withText(status)));

    }

}
