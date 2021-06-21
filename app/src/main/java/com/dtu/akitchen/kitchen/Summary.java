package com.dtu.akitchen.kitchen;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Summary {
    public Map<String, Double> users = new HashMap<>();
    public double total = 0;
    public String ended = null;

    public Summary () {
        // Default constructor required for calls to DataSnapshot.getValue(User)
    }
}
