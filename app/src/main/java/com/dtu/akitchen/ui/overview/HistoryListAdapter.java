package com.dtu.akitchen.ui.overview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtu.akitchen.R;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private HistoryFragment fragment;
    private ArrayList<String> localCreatedDateData;
    private ArrayList<Long> localUserCountData;
    private ArrayList<Double> localTotalExpenseData;
    private ArrayList<ArrayList<String>> localNameData;
    private ArrayList<ArrayList<Double>> localValueData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateTextView;
        private final TextView sizeTextView;
        private final TextView sumTextView;
        private final RecyclerView thisListView;
        //add expand view fields

        public ViewHolder(View view, HistoryFragment fragment) {
            super(view);
            dateTextView = (TextView) view.findViewById(R.id.history_log_date);
            sizeTextView = (TextView) view.findViewById(R.id.history_log_size);
            sumTextView = (TextView) view.findViewById(R.id.history_log_sum);
            thisListView = (RecyclerView) view.findViewById(R.id.this_list_view);

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
        public RecyclerView getThisListView () {
            return thisListView;
        }

    }

    //dataSet represents data to be used by the adapter
    public HistoryListAdapter(ArrayList<String> createdDate, ArrayList<Long> userCount, ArrayList<Double> totalExpense, ArrayList<ArrayList<String>> nameData, ArrayList<ArrayList<Double>> valueData, HistoryFragment fragment) {
        this.fragment = fragment;
        localCreatedDateData = createdDate;
        localUserCountData = userCount;
        localTotalExpenseData = totalExpense;
        localNameData = nameData;
        localValueData = valueData;
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

        RecyclerView recyclerView = viewHolder.getThisListView();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(fragment.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Depreciated test values
        //tempNameData = getResources().getStringArray(R.array.test_names);
        //tempValueData = getResources().getIntArray(R.array.test_values);

        CurrentListAdapter currentListAdapter = new CurrentListAdapter(localNameData.get(position), localValueData.get(position), fragment);
        recyclerView.setAdapter(currentListAdapter);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localCreatedDateData.size();
    }
}
