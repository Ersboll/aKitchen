package com.dtu.akitchen.ui.overview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dtu.akitchen.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private HistoryFragment fragment;
    private ArrayList<String> localCreatedDateData;
    private ArrayList<Long> localUserCountData;
    private ArrayList<Double> localTotalExpenseData;
    private ArrayList<String> localNameDataSet;
    private ArrayList<Double> localValuesDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateTextView;
        private final TextView sizeTextView;
        private final TextView sumTextView;
        //add expand view fields

        public ViewHolder(View view, HistoryFragment fragment) {
            super(view);
            dateTextView = (TextView) view.findViewById(R.id.history_log_date);
            sizeTextView = (TextView) view.findViewById(R.id.history_log_size);
            sumTextView = (TextView) view.findViewById(R.id.history_log_sum);
        }

        public TextView getDateTextView() {
            return dateTextView;
        }

        public TextView getSizeTextView() {
            return sizeTextView;
        }

        public TextView getSumTextView() {
            return sumTextView;
        }
    }

    //dataSet represents data to be used by the adapter
    public HistoryListAdapter(ArrayList<String> createdDate, ArrayList<Long> userCount, ArrayList<Double> totalExpense, ArrayList<String> nameData, ArrayList<Double> valueData, HistoryFragment fragment) {
        this.fragment = fragment;
        localCreatedDateData = createdDate;
        localUserCountData = userCount;
        localTotalExpenseData = totalExpense;
        localNameDataSet = nameData;
        localValuesDataSet = valueData;
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public HistoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.history_list_item, viewGroup, false);

        return new HistoryListAdapter.ViewHolder(view, fragment);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(HistoryListAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getDateTextView().setText(localCreatedDateData.get(position));
        String userCount = localUserCountData.get(position).toString() + fragment.getResources().getString(R.string.size);
        viewHolder.getSizeTextView().setText(userCount);
        String total = localTotalExpenseData.get(position).toString() + fragment.getResources().getString(R.string.dkk);
        viewHolder.getSumTextView().setText(total);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(0 == localCreatedDateData.size()) {
            return 0;
        }
        return localCreatedDateData.size();
    }

}
