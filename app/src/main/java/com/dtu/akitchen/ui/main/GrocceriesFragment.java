package com.dtu.akitchen.ui.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dtu.akitchen.R;


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

        View rootView = inflater.inflate(R.layout.fragment_grocceries, container, false);

        //set recyclerview
        grocceryListView = rootView.findViewById(R.id.grocceries_list_view);

        //set layoutmanager
        layoutManager = new LinearLayoutManager(getActivity());
        grocceryListView.setLayoutManager(layoutManager);

        //set custom made adapter for groccery items
        //TODO remove this placeholder dataSet
        dataSet = getResources().getStringArray(R.array.test_items);
        grocceryListAdapter = new GrocceryListAdapter(dataSet, this);
        grocceryListView.setAdapter(grocceryListAdapter);


        //set add button onClick

        Button addButton = rootView.findViewById((R.id.add_item_button));
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView itemTextView = (TextView) rootView.findViewById(R.id.new_item_text);
                String newItemText = itemTextView.getText().toString();
                Log.i("ADD", newItemText);
                //TODO add firebaseintegration
            }
        });


        //return the inflated flagment
        return rootView;
    }

    public void openInputPriceDialog(String itemName) {
        EnterPriceDialogFragment inputDialog = new EnterPriceDialogFragment();
        inputDialog.setTitle(itemName);
        inputDialog.show(getActivity().getSupportFragmentManager(),
                "inputDialog");
    }

}