package com.dtu.akitchen.ui.login_logout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.dtu.akitchen.R;
import com.google.firebase.auth.FirebaseAuth;

public class LogOutActivity extends AppCompatActivity {

    private String TAG = "LogOutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);

        Log.i(TAG, "Launched LogOutActivity");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();

        // When logged out the activity stack is cleared and the LoginActivity is set as the root activity
        Intent intent = new Intent(LogOutActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}