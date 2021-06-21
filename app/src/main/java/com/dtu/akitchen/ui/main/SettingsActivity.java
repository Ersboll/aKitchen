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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.databinding.ActivitySettingsBinding;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.kitchen.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    DatabaseReference myKitchenRef;
    public DatabaseReference myIsAdminRef;
    String TAG = "Settingsactivity";
    public String uid;
    DatabaseReference curKitchenRef;
    public ListView userListListView;
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
        myIsAdminRef = database.getReference("/kitchens/" + FirebaseCalls.kitchenId + "/users/" + uid + "/admin");
        curKitchenRef = database.getReference("/kitchens/" + FirebaseCalls.kitchenId);
        userListListView = new ListView(this);
        List<String> users = new ArrayList<>();
        List<String> userUids = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,users);
        userListListView.setAdapter(adapter);
        userListListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @SuppressLint("RestrictedApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.d(TAG, String.valueOf(position) + " " + id);
                String targetUserUid = userUids.get(position);
                Log.d(TAG,targetUserUid);

                if (isCurrentAdmin()){
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

        // Adding usernames to listView
        for (User user : FirebaseCalls.users.values()){
            if (!user.active) continue;
            if (user.name == null) {
                users.add("Unnamed user");
            } else {
                users.add(user.name);
            }
            userUids.add(user.getUid());
        }

    }

    private boolean isCurrentAdmin () {
        User user = FirebaseCalls.users.get(uid);
        if (user == null) return false;
        return user.admin;
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

    private void deleteKitchen () {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference kitchenRef = database.getReference("/kitchens/" + FirebaseCalls.kitchenId);
        kitchenRef.removeValue();
    }

    public void onPressLeaveKitchen (View view) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                Log.d(TAG, "Leave Kitchen");

                if (isCurrentAdmin()) {
                    int countActive = 0;
                    for (User user : FirebaseCalls.users.values()) {
                        if (user.active) countActive++;
                    }

                    if (countActive == 1) {
                        deleteKitchen();
                        myKitchenRef.removeValue();
                    }
                } else {
                    Log.d(TAG, "Removes user from kitchen");
                    curKitchenRef.child("users").child(uid).child("active").setValue(false);
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
                if (isCurrentAdmin()){
                    int countActive = 0;
                    for (User user : FirebaseCalls.users.values()) {
                        if (user.active) countActive++;
                    }
                    if (countActive == 1) {
                        @SuppressLint("ShowToast") Snackbar snackbar = Snackbar.make(view,"Delete user", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        deleteKitchen();
                        LogInOut.deleteUser();
                    } else {
                        Toast.makeText(getApplicationContext(),"Error deleting user while kitchen admin", Toast.LENGTH_LONG).show();
                    }
                } else {
                    @SuppressLint("ShowToast") Snackbar snackbar = Snackbar.make(view,"Delete user", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    curKitchenRef.child("users").child(uid).child("active").setValue(false);
                    LogInOut.deleteUser();
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Are you sure?").setPositiveButton("Delete", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
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
        builder.setView(userListListView);
        dialog = builder.create();
        dialog.show();
    }
}
