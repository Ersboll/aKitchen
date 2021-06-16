package com.dtu.akitchen.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtu.akitchen.R;

import java.util.ArrayList;


public class GrocceriesFragment extends Fragment {
    RecyclerView grocceryListView;
    GrocceryListAdapter grocceryListAdapter;
    RecyclerView.LayoutManager layoutManager;
    String[] dataSet;


    // TODO: Rename and change types and number of parameters
    public static GrocceriesFragment newInstance() {
        GrocceriesFragment fragment = new GrocceriesFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //root should possibly be main, not sure yet
        View rootView = inflater.inflate(R.layout.fragment_grocceries, container, false);

        //set recyclerview
        grocceryListView = getActivity().findViewById(R.id.grocceries_list_view);

        //set layoutmanager
        layoutManager = new LinearLayoutManager(getActivity());
        grocceryListView.setLayoutManager(layoutManager);

        //set custom made adapter for groccery items
        grocceryListAdapter = new GrocceryListAdapter(dataSet);
        grocceryListView.setAdapter(grocceryListAdapter);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grocceries, container, false);
    }
}