package com.dtu.akitchen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.dtu.akitchen.MainActivity;
import com.dtu.akitchen.R;
import com.dtu.akitchen.ui.logInOut.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_startup);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(authStateListener);

        Log.i("StartupActivity", "Launched StartupActivty");
    }
    FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null){ // Not logged in
            Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | // When logged out the activity stack is cleared and the LoginActivity is set as the root activity
                            Intent.FLAG_ACTIVITY_NO_ANIMATION); // No animation when switching
            startActivity(intent);
            finish();
        }
        if(user != null){ // Logged in
            Intent intent = new Intent(StartupActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK | // When logged out the activity stack is cleared and the MainActivity is set as the root activity
                    Intent.FLAG_ACTIVITY_NO_ANIMATION); // No animation when switching
            startActivity(intent);
            finish();
        }
    };
}