package com.dtu.akitchen.ShoppingListItems;

import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.kitchen.Summary;
import com.dtu.akitchen.kitchen.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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
        //get only the active users to update balances
        Summary summary = FirebaseCalls.summary;
        //update total amounth of money spent for this summary session
        summary.total+=price;

        //update buyers balance
        if(null==summary.users.get(userUID)){
            summary.users.put(userUID, 0.);
        }
        double currentBalance = summary.users.get(userUID);
        summary.users.put(userUID, currentBalance + price);

        int n = 0;
        ArrayList<String> activeUsers = new ArrayList<>();
        //find all active user other than the buyer, and inc counter
        for(User user : FirebaseCalls.users.values()) {
            if(user.active) {
                activeUsers.add(user.getUid());
                n++;
            }
        }

        //update all active users balances
        if(n!=0) {
            double balanceChange = price/n;

            for(String userKey : activeUsers) {
                if(null==summary.users.get(userKey)){
                    summary.users.put(userKey, 0.);
                }
                currentBalance = summary.users.get(userKey);
                summary.users.put(userKey,currentBalance - balanceChange);
            }
        }

        DatabaseReference summaryRef = databaseReference.child("kitchens").child(FirebaseCalls.kitchenId)
                .child("summaries").child("current");
        summaryRef.setValue(summary);
    }



}
