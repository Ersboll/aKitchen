package com.dtu.akitchen.authentication;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.dtu.akitchen.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class logInOut {

    public static String TAG = "logInOut";

    /**
     * Logs the current out of their session
     */
    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * Checks if
     * @param email is valid
     * @return true if it is, false if not
     */
    public static boolean isEmailValid(String email) {
        return email != null && (Patterns.EMAIL_ADDRESS.matcher((email)).matches() && !email.trim().isEmpty());
    }

    /**
     * Checks if
     * @param password is valid
     * @return true if it is, false if not
     */
    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    /**
     * Checks if 2 passwords match and are valid
     * @param password1
     * @param password2
     * @return true if they are valid, false if not
     */
    public static boolean arePasswordsValid(String password1, String password2){
        return isPasswordValid(password1) && isPasswordValid(password2) && password1.equals(password2);
    }

    /**
     * Backend login method
     * @param activity your current activity (often this)
     * @param email the user email
     * @param password the user password
     * @return true if password and email are valid, false if not
     */
    public static boolean login(Activity activity, String email, String password) {
        if(isPasswordValid(password) && isEmailValid(email)) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity,task -> {
                        if(task.isSuccessful()){
                            auth.getCurrentUser();
                        } else {
                            Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_LONG).show();
                        }
                    });
            return true;
        }
        return false;
    }

    /**
     * Backend signUp method
     * @param activity your current activity (often this)
     * @param email the user email
     * @param password the user password
     * @return true if password and email are valid, false if not
     */
    public static boolean signUp(Activity activity, String email, String password){
        if(isPasswordValid(password) && isEmailValid(email)){
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(activity,task ->{
                        if(task.isSuccessful()){
                            logout();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity, "ERROR creating user", Toast.LENGTH_SHORT).show();
                        }
                    });
            return true;
        }
        return false;
    }

    /**
     * Backed resetpassword method, sends a passwordreset link to the supplied email
     * @param activity the current actvitiy calling the method (often this)
     * @param email the user email
     * @return true if email is valid, false if not
     */
    public static boolean resetPassword(Activity activity, String email){
        if(isEmailValid(email)) {
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
     * @return a FirebaseUser object
     * @throws UserNotSignedInException if the user is not signed in
     */
    public static FirebaseUser getCurrentUser() throws UserNotSignedInException{
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            return user;
        } else {
            throw new UserNotSignedInException("The user is not signed in");
        }
    }

    /**
     * Sets the current user's email
     * @param email the new email
     * @throws IllegalArgumentException if the email is invalid
     * @throws UserNotSignedInException if the user is not signed in
     */
    public static void setUserEmail(String email) throws IllegalArgumentException,UserNotSignedInException {
        if(!isEmailValid(email))
            throw new IllegalArgumentException("Invalid email passed to setUserEmail");
        FirebaseUser user = getCurrentUser();
        user.updateEmail(email)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.d(TAG, "User email address updated");
                    }
                });
    }

    /**
     * Sets the current user's password
     * @param password the new password
     * @throws IllegalArgumentException if the password is invalid
     * @throws UserNotSignedInException if the user is not signed in
     */
    public static void setUserPassword(String password) throws IllegalArgumentException,UserNotSignedInException{
        if(!isPasswordValid(password))
            throw new IllegalArgumentException("Invalid password passed to setUserPassword");
        getCurrentUser()
                .updatePassword(password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.d(TAG, "User password updated");
                    }
                });
    }

    /**
     * Deletes the current user
     * @throws UserNotSignedInException if the user is not signed in
     */
    public static void deleteUser() throws UserNotSignedInException{
        getCurrentUser()
                .delete()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Log.d(TAG, "User account deleted");
                    } else {
                        Log.d(TAG, "Failed account deletion");
                    }
                });
    }
}
