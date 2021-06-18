package com.dtu.akitchen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.dtu.akitchen.MainActivity;
import com.dtu.akitchen.R;
import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.ui.kitchen.CreateOrJoinKitchenActivity;
import com.dtu.akitchen.ui.logInOut.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        FirebaseUser user = LogInOut.getCurrentUser();
        if (user == null){ // Not logged in
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | // When logged out the activity stack is cleared and the LoginActivity is set as the root activity
                            Intent.FLAG_ACTIVITY_NO_ANIMATION); // No animation when switching
            startActivity(intent);
            finish();
        }
        if(user != null){ // Logged in
            Intent intent = new Intent(getApplicationContext(), CreateOrJoinKitchenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // No animation when switching
            startActivity(intent);
            finish();
        }
    };
}