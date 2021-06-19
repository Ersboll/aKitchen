package com.dtu.akitchen.GrocceryItems;

import androidx.fragment.app.Fragment;

import com.dtu.akitchen.ui.main.GrocceriesFragment;

public class GrocceryItem {
    private GrocceriesFragment fragment;
    private String boughtBy;
    private int timeStamp;
    private String itemName;
    private double price;

    private DAOGrocceryItem DAO;

    public GrocceryItem(String itemName) throws BlankItemNameException {
        if(itemName.isEmpty()) {
            throw new BlankItemNameException("Item name cannot be blank");
        }
        this.itemName = itemName;
        this.DAO = new DAOGrocceryItem();
    }

    public void addItem() {
        DAO.addItem(this).addOnSuccessListener( suc ->
        {
            fragment.showShortToast("Item added");

        })
        .addOnFailureListener( err -> {
            fragment.showShortToast(err.getMessage());
        });
    }


    public void buyItem(int price, String userUID){
        this.price = price;
        this.boughtBy = userUID;
        DAO.updateItem(this);
    }

    //must set fragment to make toast messages from the fragments context
    public void setFragment(GrocceriesFragment fragment) {
        this.fragment = fragment;
    }
}
