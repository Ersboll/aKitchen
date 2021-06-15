package com.dtu.akitchen.ui.login_logout;

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
    }
}