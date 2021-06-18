package com.dtu.akitchen.ui.kitchen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.dtu.akitchen.MainActivity;
import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.databinding.ActivityCreateOrJoinKitchenBinding;
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

        String uid = LogInOut.getCurrentUser().getUid();

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
        setContentView(binding.getRoot());

        final Button createKitchenButton = binding.createKitchenButton;
        final Button joinKitchenButton = binding.joinKitchenButton;

        createKitchenButton.setOnClickListener(v->{
            // TODO create CreateKitchenActivity
            //Intent intent = new Intent(CreateOrJoinKitchenActivity.this, CreateKitchenActivity.class);
            //startActivity(intent);
            /*try { //TODO set name
            User user = new User(LogInOut.getCurrentUser().getUid(),true, "");
            Kitchen kitchen = new Kitchen("Vores køkken", user);

            FirebaseCalls.createKitchen(kitchen, user);

        } catch (UserNotSignedInException e) {
            e.printStackTrace();
        }*/
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