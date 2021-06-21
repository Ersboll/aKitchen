package com.dtu.akitchen.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.dtu.akitchen.MainActivity;
import com.dtu.akitchen.R;
import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.ui.kitchen.CreateOrJoinKitchenActivity;
import com.dtu.akitchen.ui.logInOut.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class StartupActivity extends AppCompatActivity {

    private String TAG = "Startup";
    private Boolean inStartUpActivity;
    private FirebaseUser user;
    private AuthStateListener authStateListener;
    private DatabaseReference userKitchenRef;
    private ValueEventListener inKitchenListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("StartupActivity", "Launched StartupActivty");
        inStartUpActivity = true;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_startup);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        inKitchenListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d(TAG, "Kitchen: " + value);

                if (value != null) {
                    FirebaseCalls.initialize(value);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | // When logged out the activity stack is cleared and the MainActivity is set as the root activity
                            Intent.FLAG_ACTIVITY_NO_ANIMATION); // No animation when switching
                    startActivity(intent);
                    finish();
                } else {
                    FirebaseCalls.destroy();
                    Log.d(TAG, "You have not joined a kitchen");
                    Intent intent = new Intent(getApplicationContext(), CreateOrJoinKitchenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | // When logged out the activity stack is cleared and the MainActivity is set as the root activity
                            Intent.FLAG_ACTIVITY_NO_ANIMATION); // No animation when switching
                    intent.putExtra("NOT_IN_CREATE_OR_JOIN_KITCHEN_ACTIVITY", false);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };

        authStateListener = firebaseAuth -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            user = LogInOut.getCurrentUser();
//            userKitchenRef.removeEventListener(inKitchenListener);

            if(user == null){//user is not logged in
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            } else {
                userKitchenRef = database.getReference("/users/" + user.getUid() + "/kitchen");
                userKitchenRef.addValueEventListener(inKitchenListener);
            }
        };

        auth.addAuthStateListener(authStateListener);
    }


    @Override
    protected void onPause() {
        super.onPause();
        inStartUpActivity = false;
    }
}