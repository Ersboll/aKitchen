package com.dtu.akitchen.ShoppingListItems;

import com.dtu.akitchen.authentication.LogInOut;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class kitchenHelper {
    public static String getKitchenId(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String kitchenId = databaseReference.child("users").
                child(LogInOut.getCurrentUser().getUid()).get().toString();
        return kitchenId;
    }
}
