package com.dtu.akitchen.ui.boughtItems;

public class BoughtItem {
    public double price;
    public String name;
    public String boughtBy;
    public String date;

    public BoughtItem( String name, double price ,String boughtBy, String date){
        this.name=name;
        this.price=price;
        this.boughtBy=boughtBy;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getBoughtBy() {
        return boughtBy;
    }

    public String getName() {
        return name;
    }
}
