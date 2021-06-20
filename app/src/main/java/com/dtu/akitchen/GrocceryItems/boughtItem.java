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

    private DAOboughtItem boughtItemDAO;
    private DAOshoppingListItems shoppingListDAO;

    public boughtItem(String itemName, String boughtBy) throws BlankItemNameException {
        if(itemName.isEmpty()) {
            throw new BlankItemNameException("Item name cannot be blank");
        }
        this.itemName = itemName;
        this.boughtBy = boughtBy;
        this.boughtItemDAO = new DAOboughtItem();
        this.shoppingListDAO = new DAOshoppingListItems();
    }

    public void addItem() {
        boughtItemDAO.addItem(this).addOnSuccessListener(suc ->
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

    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("itemName", itemName);
        result.put("bought_by", boughtBy);
        result.put("date", timeStamp);
        result.put("price", price);
        return result;
    }

    //must set fragment to make toast messages from the fragments context
    public void setFragment(GrocceriesFragment fragment) {
        this.fragment = fragment;
    }
}
