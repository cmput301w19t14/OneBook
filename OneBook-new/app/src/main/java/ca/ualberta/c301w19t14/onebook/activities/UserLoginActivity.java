package ca.ualberta.c301w19t14.onebook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import ca.ualberta.c301w19t14.onebook.models.User;

/**
 * This class implements the user login functionality.
 * Allows a user to login with an email and password and create will promote new account creation
 * when a user inputs an email that isn't already associated with an account.
 * @author CMPUT301 Team14: CCID
 */
public class UserLoginActivity extends AppCompatActivity {

    /**
     * Initializes the view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance().createSignInIntentBuilder().build(),
                    123);
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.

        if (requestCode == 123) {
            IdpResponse response = IdpResponse.fromResultIntent(data);


            // Successfully signed in
            if (resultCode == RESULT_OK) {
                User.updateDatabase(FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    return;
                }

                Log.e("OneBook", "Sign-in error: ", response.getError());
            }
        }
    }
}
