package com.dtu.akitchen.ShoppingListItems;

import com.dtu.akitchen.authentication.LogInOut;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Philip Hviid
public class DAOboughtItem {

    private DatabaseReference databaseReference;
    private DatabaseReference grocceryListRef;
    private FirebaseUser user;

    public DAOboughtItem() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        //change this in case database is restructured;
        user = LogInOut.getCurrentUser();
        String kitchenId = databaseReference.child("users")
                .child("kitchen").get().toString();
        //grocceryListRef = databaseReference.child("bought_items).child(kitchenId);
        grocceryListRef = databaseReference.child("bought_items").child(kitchenId);
    }

    public Task<Void> addItem(boughtItem item) {
        return null;
    }

    public Task<Void> deleteItem(String key) {
        return grocceryListRef.child(key).removeValue();
    }


    public Task<Void> updateItem(boughtItem boughtItem) {
        return null;
    }



}
