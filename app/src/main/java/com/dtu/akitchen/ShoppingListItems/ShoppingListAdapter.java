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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class ShoppingListAdapter extends
        RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private shoppingListFragment fragment;
    private ArrayList<ShoppingListItem> shoppingListItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view, shoppingListFragment fragment) {
            super(view);

            textView = (TextView) view.findViewById(R.id.groccery_item_name);
            //Open dialogue to prompt for price
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String itemName = textView.getText().toString();
                    Log.i("Buying", "clicked grocceryitem: " + itemName);
                    fragment.openInputPriceDialog(itemName);
                }
            });


        }


        public TextView getTextView() {
            return textView;
        }
    }

    //dataSet represents data to be used by the adapter
    //TODO import from firebase
    public ShoppingListAdapter(ArrayList<ShoppingListItem> shoppingListItems, shoppingListFragment fragment) {
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
        viewHolder.getTextView().setText(shoppingListItems.get(position).getItemName());
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
