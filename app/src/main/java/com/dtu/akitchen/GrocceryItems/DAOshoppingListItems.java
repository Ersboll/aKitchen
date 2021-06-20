package com.dtu.akitchen.GrocceryItems;

import android.util.Log;

import com.dtu.akitchen.authentication.UserNotSignedInException;
import com.dtu.akitchen.authentication.logInOut;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Philip Hviid
public class DAOshoppingListItems {
    private DatabaseReference databaseReference;
    private DatabaseReference shoppingListReference;
    private String kitchenId;

    private static final String TAG = "DAO";


    public DAOshoppingListItems() throws UserNotSignedInException {
        FirebaseUser user = logInOut.getCurrentUser();
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        //get the kitchen that the user belongs to
        //TODO should be done with onValueChagneListener instead
        //TODO uncomment line below when database connection is up
        kitchenId = databaseReference.child("users").child(user.getUid()).get().toString();
        Log.i(TAG, "user: " + user + ", kitchen: " + kitchenId);
        this.shoppingListReference = databaseReference.child("shopping_list").child(kitchenId);

    }

    public Task<Void> addItem(String itemName) {
        return shoppingListReference.push().setValue(itemName);
    }

    public Task<Void> deleteItem(String key) {
        return shoppingListReference.child(key).removeValue();
    }
}
