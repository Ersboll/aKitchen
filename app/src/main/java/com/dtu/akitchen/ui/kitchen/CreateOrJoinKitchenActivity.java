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
import com.dtu.akitchen.authentication.UserNotSignedInException;
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
    ValueEventListener listener;
    DatabaseReference myRef;
    private final Boolean IN_CREATE_OR_JOIN_KITCHEN_ACTIVITY = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String uid = null;
        try {
            uid = LogInOut.getCurrentUser().getUid();
        } catch (UserNotSignedInException e) {
            e.printStackTrace();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("/users/" + uid + "/kitchen");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value);
                if(value != null){
                    Log.d(TAG,"You are a member of kitchen: " + value);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | // When logged out the activity stack is cleared and the MainActivity is set as the root activity
                            Intent.FLAG_ACTIVITY_NO_ANIMATION); // No animation when switching
                    startActivity(intent);
                    finish();
                } else if (IN_CREATE_OR_JOIN_KITCHEN_ACTIVITY != null && !IN_CREATE_OR_JOIN_KITCHEN_ACTIVITY) {
                    Log.d(TAG,"You have not joined a kitchen");
                    Intent intent = new Intent(getApplicationContext(),CreateOrJoinKitchenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | // When logged out the activity stack is cleared and the MainActivity is set as the root activity
                            Intent.FLAG_ACTIVITY_NO_ANIMATION); // No animation when switching
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };

        myRef.addValueEventListener(listener);




        binding = ActivityCreateOrJoinKitchenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        final Button createKitchenButton = binding.createKitchenButton;
        final Button joinKitchenButton = binding.joinKitchenButton;

        createKitchenButton.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Kitchen details");
            builder.setIcon(R.drawable.ic_baseline_kitchen_24);
            builder.setMessage("Enter name of the kitchen");

            EditText alertInput = new EditText(this);

            builder.setView(alertInput);
            // Create dialog

            // Set button
            builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO ON Create click
                    String kitchenName = alertInput.getText().toString();
                    try {
                        User user = new User(LogInOut.getCurrentUser().getUid(),true,"");
                        Kitchen kitchen = new Kitchen(kitchenName,user);
                        FirebaseCalls.createKitchen(kitchen,user);
                    } catch (UserNotSignedInException e) {
                        e.printStackTrace();
                    }


                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog ad = builder.create();
            ad.show(); // Shows dialog button
        });

        joinKitchenButton.setOnClickListener(v -> {
            // TODO create JoinKitchenActivity
            //Intent intent = new Intent(CreateOrJoinKitchenActivity.this, JoinKitchenActivity.class)
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRef.removeEventListener(listener);
    }
}