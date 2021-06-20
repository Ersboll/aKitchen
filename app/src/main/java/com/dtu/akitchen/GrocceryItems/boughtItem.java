package com.dtu.akitchen.GrocceryItems;

import com.dtu.akitchen.ui.main.GrocceriesFragment;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class boughtItem {
    private GrocceriesFragment fragment;
    private String boughtBy;
    private int timeStamp;
    private String itemName;
    private double price;

    private DAOboughtItem DAO;

    public boughtItem(String itemName, String boughtBy) throws BlankItemNameException {
        if(itemName.isEmpty()) {
            throw new BlankItemNameException("Item name cannot be blank");
        }
        this.itemName = itemName;
        this.boughtBy = boughtBy;
        this.DAO = new DAOboughtItem();
    }

    public void addItem() {
        DAO.addItem(this).addOnSuccessListener(suc ->
        {
            fragment.showShortToast("Item added");

        })
        .addOnFailureListener( err -> {
            fragment.showShortToast(err.getMessage());
        });
    }

    //TODO gonna be replaced
    public void buyItem(int price, String userUID){
        this.price = price;
        this.boughtBy = userUID;
        DAO.updateItem(this);
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        //TODO fill in info
        return result;
    }

    //must set fragment to make toast messages from the fragments context
    public void setFragment(GrocceriesFragment fragment) {
        this.fragment = fragment;
    }
}
