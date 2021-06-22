package com.dtu.akitchen.authentication;

import android.app.Activity;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Author Niels Kjær Ersbøll
 * s183903
 */
public class LogInOut {

    public static String TAG = "LogInOut";

    /**
     * Logs the current out of their session
     */
    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * Checks if email is valid
     */
    public static boolean isEmailValid(String email) {
        return email != null && (Patterns.EMAIL_ADDRESS.matcher((email)).matches() && !email.trim().isEmpty());
    }

    /**
     * Checks if password is valid
     */
    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    /**
     * Checks if the 2 passwords match
     */
    public static boolean doPasswordsMatch(String password1, String password2) {
        return password1 != null && password1.equals(password2);
    }

    /**
     * Backend login method
     *
     * @param activity your current activity (often this)
     * @param email    the user email
     * @param password the user password
     */
    public static void login(Activity activity, String email, String password) {
        if (isPasswordValid(password) && isEmailValid(email)) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, task -> {
                        if (!task.isSuccessful()) {
                            Exception e = task.getException();
                            Log.w(TAG, "User login exception: " + e.getMessage());
                            if(e instanceof FirebaseAuthInvalidCredentialsException){
                                // The password is invalid or the user does not have a password
                                Toast.makeText(activity, "The password is invalid",Toast.LENGTH_SHORT).show();
                            } else if( e instanceof FirebaseAuthInvalidUserException) {
                                // There is no user record corresponding to this identifier. The user may have been deleted.
                                Toast.makeText(activity, "There is no account tied to the given email",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if(FirebaseCalls.kitchenId != null)
                                FirebaseDatabase.getInstance()
                                        .getReference("/kitchens/" + FirebaseCalls.kitchenId + "/users/" + getCurrentUser().getUid() + "/active")
                                        .setValue(true);

                            auth.getCurrentUser();
                        }
                    });
        }
    }

    /**
     * Backend signUp method
     *
     * @param activity your current activity (often this)
     * @param email    the user email
     * @param password the user password
     */
    public static void signUp(Activity activity, String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (!task.isSuccessful()) {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d(TAG, "User has signed up");
                    }
                });
    }


    /**
     * Backed resetpassword method, sends a passwordreset link to the supplied email
     *
     * @param activity the current actvitiy calling the method (often this)
     * @param email    the user email
     * @return true if email is valid, false if not
     */
    public static boolean resetPassword(Activity activity, String email) {
        if (isEmailValid(email)) {
            FirebaseAuth.getInstance()
                    .sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Password reset email sent");
                        }
                    });
            activity.finish();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the current user firebase element
     *
     * @return a FirebaseUser object
     */
    public static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     * Sets the current user's email
     *
     * @param email the new email
     * @throws IllegalArgumentException if the email is invalid
     */
    public static void setUserEmail(String email) throws IllegalArgumentException {
        if (!isEmailValid(email))
            throw new IllegalArgumentException("Invalid email passed to setUserEmail");
        FirebaseUser user = getCurrentUser();
        user.updateEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User email address updated");
                    }
                });
    }

    /**
     * Sets the current user's password
     *
     * @param password the new password
     * @throws IllegalArgumentException if the password is invalid
     */
    public static void setUserPassword(String password) throws IllegalArgumentException {
        if (!isPasswordValid(password))
            throw new IllegalArgumentException("Invalid password passed to setUserPassword");
        getCurrentUser()
                .updatePassword(password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User password updated");
                    }
                });
    }

    /**
     * Deletes the current user
     */
    public static void deleteUser() {
        getCurrentUser()
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User account deleted");
                    } else {
                        Log.d(TAG, "Failed account deletion");
                    }
                });
    }
}
