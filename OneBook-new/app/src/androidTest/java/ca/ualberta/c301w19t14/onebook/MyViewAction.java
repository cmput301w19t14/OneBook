package ca.ualberta.c301w19t14.onebook;

import android.view.View;

import org.hamcrest.Matcher;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

/**
 * This class is used for functionality of some tests
 * @author CMPUT301 Team14
 */
//https://stackoverflow.com/questions/28476507/using-espresso-to-click-view-inside-recyclerview-item
public class MyViewAction {

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

}