package com.dtu.akitchen.kitchen;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Kitchen {
    private static String TAG = "KITCHEN";

    public String kitchenName;
    public HashMap<String, Object> users;

    public Kitchen(){
        // Default constructor required for calls to DataSnapshot.getValue(Kitchen)
    }

    public Kitchen(String kitchenName, User userAdmin){
        this.kitchenName = kitchenName;
        users = new HashMap<>();
        users.put(userAdmin.getUid(),userAdmin);
    }

    @Exclude
    public void addUser(User newUser){
        users.put(newUser.getUid(), newUser);
    }

    @Exclude
    public void addUsers(List<User> users){
        for (User u:
             users) {
            this.users.put(u.getUid(),u);
        }
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("kitchenName", kitchenName);
        result.put("users", users);
        return result;
    }

}
