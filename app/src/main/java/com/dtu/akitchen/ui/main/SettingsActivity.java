package com.dtu.akitchen.ui.main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.databinding.ActivitySettingsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    ValueEventListener adminListener;
    ValueEventListener kitchenListener;
    DatabaseReference myKitchenRef;
    public DatabaseReference myIsAdminRef;
    String TAG = "Settingsactivity";
    public String kitchenKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String uid = LogInOut.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();



        // Getting kitchen ID. Used to identify whether a user is admin or not.
        myKitchenRef = database.getReference("/users/" + uid + "/kitchen");
        myIsAdminRef = database.getReference("/kitchens/" + kitchenKey + "/users/" + uid + "/admin");
        kitchenListener = new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                kitchenKey = snapshot.getValue(String.class);
                Log.d(TAG, "Got kitchen key: " + kitchenKey);
                myIsAdminRef = database.getReference("/kitchens/" + kitchenKey + "/users/" + uid + "/admin");
                // Add listener call to get updated data with the values from this listener
                myIsAdminRef.addValueEventListener(adminListener);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w(TAG, "Failed to read value", error.toException());
            }
        };

        // Getting whether a user is admin or not
        adminListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Boolean value = snapshot.getValue(Boolean.class);
                if(value == null){
                    Log.d(TAG, "User is admin = null");
                    Log.d(TAG, "kitchenKey: " + kitchenKey);
                } else {
                    if (value) {
                        Log.d(TAG, "User is current admin of a kitchen");
                    } else {
                        Log.d(TAG, "User is currently not the admin of a kitchen");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w(TAG, "Failed to read value", error.toException());
            }
        };
        myKitchenRef.addValueEventListener(kitchenListener);
    }

    public void onPressLeaveKitchen (View view) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                Log.d("aKitchen_settings", "Leave Kitchen");
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Are you sure?").setPositiveButton("Leave", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    public void onPressDeleteUser (View view) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                @SuppressLint("ShowToast") Snackbar snackbar = Snackbar.make(view,"Delete user", Snackbar.LENGTH_LONG);
                snackbar.show();
                LogInOut.deleteUser();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Are you sure?").setPositiveButton("Delete", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myKitchenRef.removeEventListener(kitchenListener);
        myIsAdminRef.removeEventListener(adminListener);
    }
}
