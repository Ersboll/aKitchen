package com.dtu.akitchen.ShoppingListItems;

import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.kitchen.Kitchen;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Philip Hviid
public class DAOshoppingListItems {
    private DatabaseReference databaseReference;
    private DatabaseReference shoppingListReference;
    private String kitchenId;


    public DAOshoppingListItems() {
        FirebaseUser user = LogInOut.getCurrentUser();
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        //get the kitchen that the user belongs to
        kitchenId = FirebaseCalls.kitchenId;
        this.shoppingListReference = databaseReference.child("kitchens").child(kitchenId).child("shopping_list");

    }

    public Task<Void> addItem(String itemName) {
        return shoppingListReference.push().setValue(itemName);
    }

    public Task<Void> deleteItem(String key) {
        return shoppingListReference.child(key).removeValue();
    }

}
