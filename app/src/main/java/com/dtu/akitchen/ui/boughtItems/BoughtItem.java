package com.dtu.akitchen.ui.boughtItems;

public class BoughtItem {
    public double price;
    public String name;
    public String bought_by;
    public String date;

    public BoughtItem( String name, double price ,String bought_by, String date){
        this.name=name;
        this.price=price;
        this.bought_by=bought_by;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getBought_by() {
        return bought_by;
    }

    public String getName() {
        return name;
    }
}
