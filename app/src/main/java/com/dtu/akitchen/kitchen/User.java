package com.dtu.akitchen.kitchen;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User{
    @Exclude
    private String uid;
    public boolean admin;
    public boolean active;
    public String name;

    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User)
    }

    public User(String uid, boolean admin, String name){
        this.uid = uid;
        this.admin = admin;
        this.name = name;
        this.active = true;
    }

    @Exclude
    public String getUid() {
        return uid;
    }
}
