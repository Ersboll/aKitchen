package com.dtu.akitchen.authentication;

import android.accounts.AuthenticatorException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dtu.akitchen.MainActivity;
import com.dtu.akitchen.ui.logInOut.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class logInOut {

    public static String TAG = "logInOut";

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public static boolean isUserNameValid(String username) {
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
        if(isPasswordValid(password) && isUserNameValid(email)) {
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
        if(isPasswordValid(password) && isUserNameValid(email)){
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
}
