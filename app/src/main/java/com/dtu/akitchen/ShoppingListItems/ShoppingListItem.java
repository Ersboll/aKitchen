package com.dtu.akitchen.ShoppingListItems;
//Philio Hviid
public class ShoppingListItem {
    String key;
    String itemName;

    public ShoppingListItem(String key, String itemName) {
        this.key = key;
        this.itemName = itemName;
    }


    public String getKey() {
        return key;
    }

    public String getItemName() {
        return itemName;
    }
}
