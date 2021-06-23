package com.dtu.akitchen.ui.kitchen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dtu.akitchen.MainActivity;
import com.dtu.akitchen.R;
import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.databinding.ActivityCreateOrJoinKitchenBinding;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.kitchen.Kitchen;
import com.dtu.akitchen.kitchen.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class CreateOrJoinKitchenActivity extends AppCompatActivity {
    private final String TAG = "JoinKitchen";
    private ActivityCreateOrJoinKitchenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateOrJoinKitchenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        final Button createKitchenButton = binding.createKitchenButton;
        final Button joinKitchenButton = binding.joinKitchenButton;
        final Button logOutButton = binding.logOutButton;

        logOutButton.setOnClickListener(v -> LogInOut.logout());

        createKitchenButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Kitchen details");
            builder.setIcon(R.drawable.ic_baseline_kitchen_24);
            builder.setMessage("Enter name of the kitchen");

            EditText alertInput = new EditText(this);

            builder.setView(alertInput);
            // Create dialog

            // Set button
            builder.setPositiveButton("Create", (dialog, which) -> {
                String kitchenName = alertInput.getText().toString();
                User user = new User(LogInOut.getCurrentUser().getUid(), true, null);
                Kitchen kitchen = new Kitchen(kitchenName, user);
                FirebaseCalls.createKitchen(kitchen, user);
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            AlertDialog ad = builder.create();
            ad.show(); // Shows dialog button
        });

        joinKitchenButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateOrJoinKitchenActivity.this, JoinKitchenActivity.class);
            startActivity(intent);
        });

    }
}