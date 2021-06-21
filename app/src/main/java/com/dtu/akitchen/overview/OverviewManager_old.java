package com.dtu.akitchen.overview;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dtu.akitchen.R;
import com.dtu.akitchen.ShoppingListItems.BoughtItem;
import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.kitchen.Kitchen;
import com.dtu.akitchen.kitchen.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OverviewManager_old {
/*
    private String TAG = "OverviewManager";
    private String uid;
    private Kitchen kitchen;

    private DatabaseReference kitRef;
    private DatabaseReference curRef;
    private DatabaseReference hisRef;
    private DatabaseReference usrRef;

    private ArrayList<History> histories;
    private ArrayList<String> balUserList;
    private ArrayList<Long> balList;
    private ArrayList<String> userList;
    private ArrayList<String> userNameList;
    private Long total;
    private long created;

    public OverviewManager_old(){

        uid = LogInOut.getCurrentUser().getUid();

        //add call to get current kitchen

        histories = new ArrayList<>();
        balUserList = new ArrayList<>();
        balList = new ArrayList<>();
        userList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        kitRef = database.getReference("/kitchens/kitchen1");
        curRef = kitRef.child("summaries").child("current");
        hisRef = kitRef.child("summaries").child("history");
        usrRef = kitRef.child("users");

        curRef.addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    Log.i(TAG,"curRef onDataChange called");

                    if(snapshot.child("users").exists()) {
                        Log.i(TAG, "Exists");
                    }

                    for (DataSnapshot snap: snapshot.child("users").getChildren()) {
                        String temp = snap.getKey();
                        Log.i(TAG,temp);
                        balUserList.add(temp);
                    }

                    for (DataSnapshot snap: snapshot.child("users").getChildren()) {
                        balList.add(snap.getValue(Long.class));
                    }

                    if(snapshot.child("total").exists()){
                        total = snapshot.child("total").getValue(Long.class);
                    } else {
                        Log.i(TAG, "Current total not found!");
                    }

                    if(snapshot.child("created").exists()){
                        created = snapshot.child("created").getValue(Long.class);
                    } else {
                        Log.i(TAG, "created date not found!");
                    }

                    if(balUserList.isEmpty()){
                        Log.i(TAG, "No users with balances found!");
                    }

                    if(balList.isEmpty()){
                        Log.i(TAG, "No user balances found!");
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Log.w(TAG, "Firebase VEL cancelled", error.toException());
                }
            }
        );

        hisRef.addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    Log.i(TAG,"hisRef onDataChange called");

                    for (DataSnapshot snap: snapshot.getChildren()) {
                        histories.add(snap.getValue(History.class));
                    }

                    if(histories.isEmpty()){
                        Log.i(TAG, "No history objects found");
                    }


                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Log.w(TAG, "Firebase VEL cancelled", error.toException());
                }
            }
        );

    }
*/

    /*
    public boolean settleAccounts(){
        boolean result = false;
        Date currentDate = Calendar.getInstance().getTime();

        //String date = new SimpleDateFormat("dd/mm/yyyy").format(currentDate);
        //String time = new SimpleDateFormat("HH:mm:ss").format(currentDate);

        //Date lastDate = firebase magic that gets the date of the last settle

        //for (User user:users){
        //Get user attributes: ID, name, balance
        //}

        //Create history dir in database
        //Add users: ID, name, balance
        //Add dates: currentDate, lastDate
        //Add total: prices from boughtitems added together

        //for (User user:users) {
        //Set account balance to zero
        //}

        //Check if any errors occurred, if not set
        result = true;

        return result;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }
*/
}
