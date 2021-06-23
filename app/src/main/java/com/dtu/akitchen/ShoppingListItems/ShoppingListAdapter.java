package com.dtu.akitchen.ShoppingListItems;
//Philip Hviid
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dtu.akitchen.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ShoppingListAdapter extends
        RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private ShoppingListFragment fragment;
    private ArrayList<ShoppingListItem> shoppingListItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private String itemKey;

        public ViewHolder(View view, ShoppingListFragment fragment) {
            super(view);
            textView = (TextView) view.findViewById(R.id.groccery_item_name);
            //Open dialogue to prompt for price
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String itemName = textView.getText().toString();
                    Log.i("Buying", "clicked grocceryitem: " + itemName + "," +
                            "with itemkey: " + itemKey);
                    fragment.openInputPriceDialog(itemName, itemKey);
                }
            });



        }

        public void setItemKey(String key) {
            itemKey = key;
        }

        public TextView getTextView() {
            return textView;
        }
    }

    //dataSet represents data to be used by the adapter
    public ShoppingListAdapter(ArrayList<ShoppingListItem> shoppingListItems, ShoppingListFragment fragment) {
        this.fragment = fragment;
        this.shoppingListItems = shoppingListItems;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grocceries_list_item, viewGroup, false);

        return new ViewHolder(view, fragment);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        ShoppingListItem item = shoppingListItems.get(position);
        viewHolder.getTextView().setText(item.getItemName());
        Log.i("itemKey", "setting key to: " + item.getKey());
        viewHolder.setItemKey(item.getKey());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(null == shoppingListItems) {
            return 0;
        }
        return shoppingListItems.size();
    }

}
