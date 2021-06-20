package com.dtu.akitchen.ShoppingListItems;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class BoughtItem {
    private ShoppingListFragment fragment;
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


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("itemName", itemName);
        result.put("price", price);
        result.put("bought_by", boughtBy);
        result.put("date", date);
        return result;
    }

    //must set fragment to make toast messages from the fragments context
    public void setFragment(ShoppingListFragment fragment) {
        this.fragment = fragment;
    }


}
