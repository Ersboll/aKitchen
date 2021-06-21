package com.dtu.akitchen.ui.main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.databinding.ActivitySettingsBinding;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.kitchen.User;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    ValueEventListener adminListener;
    ValueEventListener kitchenListener;
    DatabaseReference myKitchenRef;
    public DatabaseReference myIsAdminRef;
    String TAG = "Settingsactivity";
    public String kitchenKey;
    private boolean isCurrentAdmin;
    public String uid;
    DatabaseReference curKitchenRef;
    public ListView UserListListView;
    ValueEventListener userListListener;
    DatabaseReference userListRef;
    public AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(binding.getRoot());

        uid = LogInOut.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myKitchenRef = database.getReference("/users/" + uid + "/kitchen");
        myIsAdminRef = database.getReference("/kitchens/" + kitchenKey + "/users/" + uid + "/admin");
        curKitchenRef = database.getReference("/kitchens/" + FirebaseCalls.kitchenId);
        UserListListView = new ListView(this);
        List<String> users = new ArrayList<>();
        List<String> userUids = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,users);
        UserListListView.setAdapter(adapter);
        UserListListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @SuppressLint("RestrictedApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.d(TAG, String.valueOf(position) + " " + id);
                String targetUserUid = userUids.get(position);
                Log.d(TAG,targetUserUid);

                if (isCurrentAdmin){
                    Log.d(TAG, kitchenKey);
                    dialog.dismiss();
                    if (!uid.equals(targetUserUid)) {
                        DatabaseReference setNewAdminRef = curKitchenRef.child("users").child(targetUserUid).child("admin");
                        setNewAdminRef.setValue(true);
                        DatabaseReference setOldAdminRef = curKitchenRef.child("users").child(uid).child("admin");
                        setOldAdminRef.setValue(false);
                        Log.d(TAG, setNewAdminRef.getPath() + "path");
                    }
                    recreate();
                }else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"You dont have permission to set a new admin",Toast.LENGTH_LONG).show();
                    recreate();
                }
            }
        });




        // Getting kitchen ID. Used to identify whether a user is admin or not.

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
                        isCurrentAdmin = true;
                    } else {
                        Log.d(TAG, "User is currently not the admin of a kitchen");
                        isCurrentAdmin = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w(TAG, "Failed to read value", error.toException());
            }
        };
        myKitchenRef.addValueEventListener(kitchenListener);

        // Adding usernames to listView
        for (User user : FirebaseCalls.users.values()){
            users.add(user.name);
            userUids.add(user.getUid());
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onPressUpdateName (View view) {
        String newName = Objects.requireNonNull(binding.nameText.getText()).toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myName = database.getReference("/kitchens/" + FirebaseCalls.kitchenId + "/users/" + uid + "/name");
        myName.setValue(newName);
    }

    public void onPressLeaveKitchen (View view) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                Log.d(TAG, "Leave Kitchen");
                if (isCurrentAdmin){
                    Log.d(TAG,"Leave kitchen error::While admin");
                    Toast.makeText(getApplicationContext(),"Error leaving kitchen while admin",Toast.LENGTH_LONG).show();
                } else{
                    Log.d(TAG, "Removes user from kitchen");
                    DatabaseReference kitchenUserReference = curKitchenRef.child("users").child(uid);
                    kitchenUserReference.child("active").removeValue();
                    kitchenUserReference.child("name").removeValue();
                    kitchenUserReference.child("admin").removeValue();
                    myKitchenRef.removeValue(); // Removes current user from the current kitchen
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Are you sure?").setPositiveButton("Leave", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    public void onPressDeleteUser (View view) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                if (isCurrentAdmin){
                    Toast.makeText(getApplicationContext(),"Error deleting user while kitchen admin", Toast.LENGTH_LONG).show();
                } else {
                    @SuppressLint("ShowToast") Snackbar snackbar = Snackbar.make(view,"Delete user", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    LogInOut.deleteUser();
                }
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

    public void onPressSetNewAdmin(View view) {

        DialogInterface.OnClickListener dialogClickListener = ((dialog, which) -> {
            if (which == DialogInterface.BUTTON_NEUTRAL){
                dialog.dismiss();
                recreate();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Select user")
                .setNeutralButton("Cancel",dialogClickListener);
        builder.setCancelable(false);
        builder.setView(UserListListView);
        dialog = builder.create();
        dialog.show();
    }
}
