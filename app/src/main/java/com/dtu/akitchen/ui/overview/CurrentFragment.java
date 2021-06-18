package com.dtu.akitchen.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtu.akitchen.R;

public class CurrentFragment extends Fragment {

    RecyclerView recyclerView;
    CurrentListAdapter currentListAdapter;
    RecyclerView.LayoutManager layoutManager;
    String[] tempData;

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

        //TODO: find this id and fix
        recyclerView = root.findViewById(R.id.current_list_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tempData = getResources().getStringArray(R.array.test_names);
        currentListAdapter = new CurrentListAdapter(tempData, this);
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