package com.dtu.akitchen.kitchen;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FirebaseCalls {

    /**
     * Creates a kitchen with a specified Kitchen and User object
     * @param kitchen the Kitchen object that will be added to the database
     * @param useradmin the User object that will be set as the kitchen admin
     */
    public static void createKitchen(@NotNull Kitchen kitchen, @NotNull User useradmin){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        String key = myRef.child("kitchens").push().getKey();
        Map<String, Object> kitchenValues = kitchen.toMap();
        Map<String, Object> userValues = new HashMap<>();
        userValues.put("kitchen", key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/kitchens/" + key, kitchenValues);
        childUpdates.put("/users/" + useradmin.getUid() + "/",userValues);

        myRef.updateChildren(childUpdates);
    }

}
