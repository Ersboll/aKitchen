package com.dtu.akitchen.ui.overview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dtu.akitchen.R;

import org.jetbrains.annotations.NotNull;

public class CurrentListAdapter extends RecyclerView.Adapter<CurrentListAdapter.ViewHolder> {

    private CurrentFragment fragment;
    private String[] localNameDataSet;
    private int[] localValuesDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView valueTextView;

        public ViewHolder(View view, CurrentFragment fragment) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.current_user_name);
            valueTextView = (TextView) view.findViewById(R.id.current_user_value);
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public TextView getValueTextView() {
            return valueTextView;
        }
    }

    //dataSet represents data to be used by the adapter
    //TODO import from firebase
    public CurrentListAdapter(String[] dataSetNames, int[] dataSetValues, CurrentFragment fragment) {
        this.fragment = fragment;
        localNameDataSet = dataSetNames;
        localValuesDataSet = dataSetValues;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public CurrentListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.current_list_item, viewGroup, false);

        return new CurrentListAdapter.ViewHolder(view, fragment);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CurrentListAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getNameTextView().setText(localNameDataSet[position]);
        String color = localValuesDataSet[position] + fragment.getResources().getString(R.string.dkk);
        viewHolder.getValueTextView().setText(color);
        if(localValuesDataSet[position] < 0){
            viewHolder.getValueTextView().setTextColor(Color.RED);
        } else {
            viewHolder.getValueTextView().setTextColor(Color.parseColor("#00bd03"));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(null == localNameDataSet) {
            return 0;
        }
        return localNameDataSet.length;
    }

}
