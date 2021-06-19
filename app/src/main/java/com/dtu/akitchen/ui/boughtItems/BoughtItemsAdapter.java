package com.dtu.akitchen.ui.boughtItems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dtu.akitchen.R;

import java.util.ArrayList;

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
        String name = getItem(position).name;
        String  boughtBy = getItem(position).getBoughtBy();
        String  date= getItem(position).getDate();
        double price = getItem(position).price;

        BoughtItem item = new BoughtItem(name, 1.1, boughtBy, "20202");
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView  tvName = (TextView) convertView.findViewById(R.id.bought_item_name);
        TextView  tvBoughtBy = (TextView) convertView.findViewById(R.id.bought_item_bought_by);
        TextView  tvPrice = (TextView) convertView.findViewById(R.id.bought_item_price);
        TextView  tvDate = (TextView) convertView.findViewById(R.id.bought_item_date);

        tvName.setText(name);
        tvBoughtBy.setText(boughtBy);
        tvDate.setText(date);
        tvPrice.setText(Double.toString(price));
        return convertView;
    }

}
