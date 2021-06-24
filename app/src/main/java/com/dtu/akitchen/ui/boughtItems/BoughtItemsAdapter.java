package com.dtu.akitchen.ui.boughtItems;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import com.dtu.akitchen.R;
import com.dtu.akitchen.ShoppingListItems.BoughtItem;
import com.dtu.akitchen.ShoppingListItems.DAOboughtItem;
import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class BoughtItemsAdapter extends ArrayAdapter<BoughtItem> {

    ArrayList<BoughtItem> boughtItemsList = new ArrayList<>();
    Context mContext;
    int mResource;
    public BoughtItemsAdapter (Context context, int resourceId, ArrayList<BoughtItem> boughtItemsList){
        super(context, resourceId, boughtItemsList);
        mContext=context;
        mResource=resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).itemName;
        String  boughtBy = getItem(position).getBoughtBy();
        String  date= getItem(position).getDate();
        double price = getItem(position).price;
        String itemId = getItem(position).getId();
        String userName = FirebaseCalls.users.get(boughtBy).name;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView  tvName = (TextView) convertView.findViewById(R.id.bought_item_name);
        TextView  tvBoughtBy = (TextView) convertView.findViewById(R.id.bought_item_bought_by);
        TextView  tvPrice = (TextView) convertView.findViewById(R.id.bought_item_price);
        TextView  tvDate = (TextView) convertView.findViewById(R.id.bought_item_date);
        ImageButton removeButton = convertView.findViewById(R.id.remove_text);

        String loggedInUser = LogInOut.getCurrentUser().getUid();

        if(loggedInUser.equals(boughtBy) || FirebaseCalls.isCurrentAdmin()){
            removeButton.setVisibility(View.VISIBLE);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeButtonClickHandler(itemId);
                }
            });
        }

        tvName.setText(name);
        tvBoughtBy.setText(userName);
        tvDate.setText(date);
        tvPrice.setText(String.format(Locale.US, "%.2f", price) + " DKK");
        return convertView;
    }

    public void removeButtonClickHandler(String itemId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("kitchens").child(FirebaseCalls.kitchenId).child("bought_items").child(itemId);
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot == null) return;
                String userId = snapshot.child("bought_by").getValue(String.class);
                Double price = snapshot.child("price").getValue(Double.class);
                if (userId == null || price == null) return;

                DAOboughtItem daOboughtItem = new DAOboughtItem();
                daOboughtItem.updateBalances(userId, -price);
                snapshot.getRef().removeValue();
            }
        });
    }

}
