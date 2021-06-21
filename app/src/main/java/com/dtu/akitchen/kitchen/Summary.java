package com.dtu.akitchen.kitchen;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

@IgnoreExtraProperties
public class Summary {
    public Map<String, Double> users;
    public double total;
    public String ended;

    public Summary () {
        // Default constructor required for calls to DataSnapshot.getValue(User)
    }
}
