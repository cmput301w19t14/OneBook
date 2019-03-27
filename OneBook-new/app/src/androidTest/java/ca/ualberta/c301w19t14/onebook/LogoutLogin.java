package ca.ualberta.c301w19t14.onebook;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class LogoutLogin {

    public boolean complete;
    public String email;
    public String password;
    public String check_name;
    public String actual_name;

    @Rule
    public ActivityTestRule<EditUserActivity> activityRule =
            new ActivityTestRule<EditUserActivity>(EditUserActivity.class, false, false);


    @Test
    public void CheckLogoutLogin(){

        email = "UITest@gmail.com";
        password = "test123";
        actual_name = "John Smith";


        FirebaseAuth.getInstance().signOut();
        boolean UserLoaded = false;

        Task<AuthResult> result = FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password);

        Log.d("MYDEBUG", "executing listener");
        result.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                check_name = task.getResult().getUser().getDisplayName();
                complete = true;
            }
        });



        while (true) {

            if (complete){
                Log.d("MYDEBUG", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                Intent i = new Intent();
                activityRule.launchActivity(i);
                Assert.assertEquals(actual_name, check_name);
                return;
            }
        }

        /*
        while(true){
            if (UserLoaded){
                Intent i = new Intent();
                activityRule.launchActivity(i);
                Assert.assertTrue(true);
                return;
            }
        }
        */

        //Assert.assertNotNull(FirebaseAuth.);



    }

}
