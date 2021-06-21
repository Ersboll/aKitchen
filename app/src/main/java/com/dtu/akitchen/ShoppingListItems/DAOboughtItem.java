package com.dtu.akitchen.ShoppingListItems;

import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.kitchen.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

//Philip Hviid
public class DAOboughtItem {

    private DatabaseReference databaseReference;
    private DatabaseReference boughtItemsRef;

    public DAOboughtItem() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        //change this in case database is restructured;
        String kitchenId = FirebaseCalls.kitchenId;
        boughtItemsRef = databaseReference.child("kitchens").child(kitchenId).child("bought_items");
    }

    public Task<Void> addItem(BoughtItem item) {
        return boughtItemsRef.push().setValue(item.toMap());
    }

    public Task<Void> deleteItem(String key) {
        return boughtItemsRef.child(key).removeValue();
    }


    public void upDateBalances(String userUID, double price) {
        DatabaseReference summaryRef = databaseReference.child("kitchens").child(FirebaseCalls.kitchenId)
                .child("summaries").child("current");
        Map<String, User> userMap = FirebaseCalls.users;

    }



}
