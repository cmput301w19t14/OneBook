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
import ca.ualberta.c301w19t14.onebook.fragements.MyacctFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


public class EditProfileTest {

    public boolean complete = false;
    public String email = "UITest4@gmail.com";
    public String name = "Terry Crews";
    public String password = "testing";

    private Globals globals;

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

    }

    public void StartMyAccountFragment(){
        MyacctFragment myacctFragment = new MyacctFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myacctFragment);
        fragmentTransaction.commit();
    }

}
