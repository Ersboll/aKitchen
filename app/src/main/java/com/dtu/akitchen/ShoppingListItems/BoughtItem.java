package com.dtu.akitchen.ShoppingListItems;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class BoughtItem {
    private shoppingListFragment fragment;
    public double price;
    public String itemName;
    public String boughtBy;
    public String date;

    private DAOboughtItem DAO;

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getBoughtBy() {
        return boughtBy;
    }

    public String getItemName() {
        return itemName;
    }

    public BoughtItem(String itemName, double price, String boughtBy, String date) {
        this.itemName = itemName;
        this.boughtBy = boughtBy;
        this.price = price;
        this.DAO = new DAOboughtItem();
        this.date = date;
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
    public void setFragment(shoppingListFragment fragment) {
        this.fragment = fragment;
    }


}
