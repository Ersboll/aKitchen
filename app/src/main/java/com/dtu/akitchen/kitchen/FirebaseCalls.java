package com.dtu.akitchen.kitchen;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class FirebaseCalls {

    public static String kitchenId = null;
    public static Map<String, User> users = new HashMap<>();
    public static Summary summary = null;

    private static final ValueEventListener kitchenUsersListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            users.clear();
            for (DataSnapshot dataSnapshot :snapshot.getChildren()) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null) continue;
                user.setUid(dataSnapshot.getKey());
                users.put(dataSnapshot.getKey(), user);
            }
        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {}
    };

    private static final ValueEventListener summaryListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            summary = snapshot.getValue(Summary.class);
        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {}
    };

    public static void initialize (String newkitchenId) {
        kitchenId = newkitchenId;
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference()
                .child("kitchens").child(kitchenId).child("users");
        usersRef.addValueEventListener(kitchenUsersListener);
        DatabaseReference summaryRef = FirebaseDatabase.getInstance().getReference()
                .child("kitchens").child(kitchenId).child("summaries").child("current");
        summaryRef.addValueEventListener(summaryListener);
    }

    public static void destroy () {
        if (kitchenId != null) {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference()
                    .child("kitchens").child(kitchenId).child("users");
            usersRef.removeEventListener(kitchenUsersListener);
            DatabaseReference summaryRef = FirebaseDatabase.getInstance().getReference()
                    .child("kitchens").child(kitchenId).child("summaries").child("current");
            summaryRef.removeEventListener(summaryListener);
        }
        kitchenId = null;
    }

    /**
     * Creates a kitchen with a specified Kitchen and User object
     * @param kitchen the Kitchen object that will be added to the database
     * @param useradmin the User object that will be set as the kitchen admin
     */
    public static void createKitchen(@NotNull Kitchen kitchen, @NotNull User useradmin){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        kitchenId = Kitchen.generateId();
        Map<String, Object> kitchenValues = kitchen.toMap();
        Map<String, Object> userValues = new HashMap<>();
        userValues.put("kitchen", kitchenId);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/kitchens/" + kitchenId, kitchenValues);
        childUpdates.put("/users/" + useradmin.getUid() + "/",userValues);

        myRef.updateChildren(childUpdates);
    }

}
