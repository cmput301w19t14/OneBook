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

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import ca.ualberta.c301w19t14.onebook.activities.MainActivity;
import ca.ualberta.c301w19t14.onebook.fragments.MyProfileFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * This tests checks to see if a user can edit details on his/her profile
 * @author CMPUT301 Team14
 */

public class EditProfileTest {

    //Used for functionality
    private Methods methods = new Methods();
    private Globals globals;

    //Log in details
    public boolean complete = false;
    public String email = "UITest4@gmail.com";
    public String name1 = "Terry Crews";
    public String name2 = "Tom Cruise";
    public String password = "testing";


    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class, false, false);


    @Before
    public void init(){
        FirebaseAuth.getInstance().signOut();

        Task<AuthResult> result = FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password);

        Log.d("MYDEBUG", "executing listener");
        result.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
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

        StartMyAccountFragment();
    }


    @Test
    public void TestEditProfile(){

        //should be at my account. Next, click on the edit button
        onView(withId(R.id.pencil_button)).perform(click());

        //now we should be at the edit page activity
        onView(withId(R.id.editName)).perform(replaceText(""));
        onView(withId(R.id.editName)).perform(typeText(name2));

        //Hide the keyboard
        Espresso.closeSoftKeyboard();

        //click save
        onView(withId(R.id.UserSaveButton)).perform(click());

        //DEBUG - remove later
        methods.InfiniteLoop();

    }

    public void StartMyAccountFragment(){
        MyProfileFragment myProfileFragment = new MyProfileFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myProfileFragment);
        fragmentTransaction.commit();
    }

}
