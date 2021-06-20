package com.dtu.akitchen.ShoppingListItems;

import com.dtu.akitchen.authentication.LogInOut;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Philip Hviid
public class DAOboughtItem {

    private DatabaseReference databaseReference;
    private DatabaseReference soppingListRef;
    private FirebaseUser user;

    public DAOboughtItem() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        //change this in case database is restructured;
        user = LogInOut.getCurrentUser();
        String kitchenId = kitchenHelper.getKitchenId();
        //grocceryListRef = databaseReference.child("bought_items).child(kitchenId);
        soppingListRef = databaseReference.child("bought_items").child(kitchenId);
    }

    public Task<Void> addItem(BoughtItem item) {
        return null;
    }

    public Task<Void> deleteItem(String key) {
        return soppingListRef.child(key).removeValue();
    }


    public Task<Void> updateItem(BoughtItem boughtItem) {
        return null;
    }



}
