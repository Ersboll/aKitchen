package com.dtu.akitchen.ShoppingListItems;

import com.dtu.akitchen.authentication.LogInOut;
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
        //TODO should be done with onValueChagneListener instead
        //TODO uncomment line below when database connection is up
        kitchenId = databaseReference.child("users").child(user.getUid()).get().toString();
        this.shoppingListReference = databaseReference.child("shopping_list").child(kitchenId);

    }

    public Task<Void> addItem(String itemName) {
        return shoppingListReference.push().setValue(itemName);
    }

    public Task<Void> deleteItem(String key) {
        return shoppingListReference.child(key).removeValue();
    }

}
