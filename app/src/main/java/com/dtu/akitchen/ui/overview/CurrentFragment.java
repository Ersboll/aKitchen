package com.dtu.akitchen.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtu.akitchen.R;

public class CurrentFragment extends Fragment {

    RecyclerView recyclerView;
    CurrentListAdapter currentListAdapter;
    RecyclerView.LayoutManager layoutManager;
    String[] tempNameData;
    int[] tempValueData;

    public static CurrentFragment newInstance() {
        CurrentFragment currentFragment = new CurrentFragment();
        Bundle bundle = new Bundle();
        return currentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_current, container, false);

        recyclerView = root.findViewById(R.id.current_list_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tempNameData = getResources().getStringArray(R.array.test_names);
        tempValueData = getResources().getIntArray(R.array.test_values);
        currentListAdapter = new CurrentListAdapter(tempNameData, tempValueData, this);
        recyclerView.setAdapter(currentListAdapter);

        Button concludeButton = root.findViewById(R.id.conclude_button);
        concludeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Create summary of current user debts, send to history list and restart tally
                //TODO: Firebase integration
            }
        });

        return root;
    }
}