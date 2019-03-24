package ca.ualberta.c301w19t14.onebook;

import org.junit.Rule;

import androidx.test.rule.ActivityTestRule;

public class TestUserRequests {

    public String requester_email = "RequesterMike@gmail.com";
    public String requester_name = "Mike Scott";
    public String requester_pass = "request";

    public String owner_email = "OwnerTim@gmail.com";
    public String owner_name = "Tim Watto";
    public String owner_pass = "owner1";

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class, false, false);

}
