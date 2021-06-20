package com.dtu.akitchen.kitchen;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@IgnoreExtraProperties
public class Kitchen {
    private static String TAG = "KITCHEN";
    private final static int ID_LENGTH = 8;

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

    public static String generateId () {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder id = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < ID_LENGTH; i++) {
            int idx = rnd.nextInt(abc.length());
            id.append(abc.charAt(idx));
        }
        return id.toString();
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
