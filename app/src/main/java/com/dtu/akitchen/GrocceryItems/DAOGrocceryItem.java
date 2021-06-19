package com.dtu.akitchen.GrocceryItems;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOGrocceryItem {

    private DatabaseReference databaseReference;

    public DAOGrocceryItem()
    {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public Task<Void> addItem(GrocceryItem item) {
        return null;
    }

    public Task<Void> deleteItem(GrocceryItem item) {
        return null;
    }


    public Task<Void> updateItem(GrocceryItem grocceryItem) {
        return null;
    }
}
