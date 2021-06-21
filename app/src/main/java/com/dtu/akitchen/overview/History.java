package com.dtu.akitchen.overview;

import java.util.ArrayList;

public class History {

    public String ended;
    public Long total;
    public ArrayList<String> users;
    public ArrayList<Long> bal;

    public History(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}
