package com.dtu.akitchen.ui.kitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dtu.akitchen.R;
import com.dtu.akitchen.authentication.UserNotSignedInException;
import com.dtu.akitchen.authentication.logInOut;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.kitchen.Kitchen;
import com.dtu.akitchen.kitchen.User;

public class CreateOrJoinKitchenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join_kitchen);


        try { //TODO set name
            User user = new User(logInOut.getCurrentUser().getUid(),true, "");
            Kitchen kitchen = new Kitchen("Vores køkken", user);

            FirebaseCalls.createKitchen(kitchen, user);

        } catch (UserNotSignedInException e) {
            e.printStackTrace();
        }
    }
}