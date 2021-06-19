package com.dtu.akitchen.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtu.akitchen.R;

import java.util.Date;

public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    HistoryListAdapter historyListAdapter;
    RecyclerView.LayoutManager layoutManager;
    String[] tempDateData;
    int[] tempSizeData;
    int[] tempSumData;

    public static HistoryFragment newInstance() {
        HistoryFragment historyFragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        return historyFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = root.findViewById(R.id.history_list_view);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tempDateData = getResources().getStringArray(R.array.test_dates);
        tempSizeData = getResources().getIntArray(R.array.test_sizes);
        tempSumData = getResources().getIntArray(R.array.test_sums);
        historyListAdapter = new HistoryListAdapter(tempDateData, tempSizeData, tempSumData, this);
        recyclerView.setAdapter(historyListAdapter);

        Button exportButton = root.findViewById(R.id.export_button);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Export selected list entry as xlsx/pdf
                //TODO: Firebase integration
            }
        });

        return root;
    }
}