package com.dtu.akitchen.ui.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dtu.akitchen.R;

import org.jetbrains.annotations.NotNull;


public class GrocceryListAdapter extends
        RecyclerView.Adapter<GrocceryListAdapter.ViewHolder> {

    private GrocceriesFragment fragment;
    private String[] localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view, GrocceriesFragment fragment) {
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
    public GrocceryListAdapter(String[] dataSet, GrocceriesFragment fragment) {
        this.fragment = fragment;
        localDataSet = dataSet;
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
        viewHolder.getTextView().setText(localDataSet[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(null == localDataSet) {
            return 0;
        }
        return localDataSet.length;
    }

}
