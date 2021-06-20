package com.dtu.akitchen.overview;

import com.dtu.akitchen.ShoppingListItems.BoughtItem;
import com.dtu.akitchen.kitchen.Kitchen;
import com.dtu.akitchen.kitchen.User;

import java.util.Calendar;
import java.util.Date;

public class OverviewManager {

    private String TAG = "OverviewManager";
    private User users[];
    private Kitchen kitchen;
    private BoughtItem items[];

    public OverviewManager(){

    }

    public OverviewManager(User users[], Kitchen kitchen, BoughtItem items[]){
        this.users = users;
        this.kitchen = kitchen;
        this.items = items;
    }

    public boolean settleAccounts(){
        boolean result = false;
        Date currentDate = Calendar.getInstance().getTime();

        //String date = new SimpleDateFormat("dd/mm/yyyy").format(currentDate);
        //String time = new SimpleDateFormat("HH:mm:ss").format(currentDate);

        //Date lastDate = firebase magic that gets the date of the last settle

        //for (User user:users){
            //Get user attributes: ID, name, balance
        //}

        //Create history dir in database
            //Add users: ID, name, balance
            //Add dates: currentDate, lastDate
            //Add total: prices from boughtitems added together

        //for (User user:users) {
            //Set account balance to zero
        //}

        //Check if any errors occurred, if not set result = true

        return result;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public User[] getUsers() {
        return users;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setItems(BoughtItem[] items) {
        this.items = items;
    }

    public BoughtItem[] getItems() {
        return items;
    }
}
