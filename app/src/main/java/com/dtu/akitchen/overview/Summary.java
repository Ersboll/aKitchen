package com.dtu.akitchen.overview;

import com.dtu.akitchen.kitchen.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Summary {
    Date date;
    double total;
    HashMap<String, Double> overView;

    public Summary(Map<String, User> userMap) {
        this.overView = new HashMap<String, Double>();

    }
}
