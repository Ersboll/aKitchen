package com.dtu.akitchen.authentication;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.dtu.akitchen.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logInOut {

    public static String TAG = "logInOut";

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public static boolean isEmailValid(String username) {
        return username != null && (Patterns.EMAIL_ADDRESS.matcher((username)).matches() && !username.trim().isEmpty());
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public static boolean arePasswordsValid(String password1, String password2){
        return !(password1 == null || password2 == null) && password1.trim().length() >= 5 && password1.equals(password2) && !password1.isEmpty();
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
                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
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
}
