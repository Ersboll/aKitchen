package com.dtu.akitchen.ui.overview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dtu.akitchen.R;
import com.dtu.akitchen.ui.main.GrocceriesFragment;
import com.dtu.akitchen.ui.main.GrocceryListAdapter;

import org.jetbrains.annotations.NotNull;

public class CurrentListAdapter extends RecyclerView.Adapter<CurrentListAdapter.ViewHolder> {

    private CurrentFragment fragment;
    private String[] localDataSet;

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
    public CurrentListAdapter(String[] dataSet, CurrentFragment fragment) {
        this.fragment = fragment;
        localDataSet = dataSet;
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
        viewHolder.getNameTextView().setText(localDataSet[position]);
        //TODO: implement getValueTextView aswell
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
