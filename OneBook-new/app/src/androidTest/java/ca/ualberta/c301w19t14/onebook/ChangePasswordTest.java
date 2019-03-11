package ca.ualberta.c301w19t14.onebook;


import android.content.Intent;
import android.os.Bundle;

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

public class ChangePasswordTest {

    @Rule
    public ActivityTestRule<EditUserActivity> activityRule =
            new ActivityTestRule<EditUserActivity>(EditUserActivity.class);


    @Test
    public void CheckChangePassword(){

        /*
        Task<AuthResult> result = FirebaseAuth.getInstance().signInWithEmailAndPassword(
                "dustinmcrorie@gmail.com", "hunter2");

        Assert.assertNotNull(FirebaseAuth.);

        */

    }

}
