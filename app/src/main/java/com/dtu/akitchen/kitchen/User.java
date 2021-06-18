package com.dtu.akitchen.kitchen;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User{
    private String uid;
    public boolean admin;
    private String name;

    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User)
    }

    public User(String uid, boolean admin, String name){
        this.uid = uid;
        this.admin = admin;
    }

    public String getUid() {
        return uid;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid",uid);
        result.put("admin",admin);

        return result;
    }
}
