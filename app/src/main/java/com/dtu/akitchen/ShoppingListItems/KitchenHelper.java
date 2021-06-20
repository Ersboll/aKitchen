package com.dtu.akitchen.ShoppingListItems;

import androidx.annotation.NonNull;

import com.dtu.akitchen.authentication.LogInOut;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
//TODO replace this placeholder
public class KitchenHelper {
    public static String getKitchenId(){
        return "kitchen1";
    }
    /*
    public static String getKitchenId(){
        final String[] kitchenId = new String[1];
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(LogInOut.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                kitchenId[0] = snapshot.child("kitchen").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return kitchenId[0];
    }

     */
}
