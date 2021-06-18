package com.dtu.akitchen.ui.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dtu.akitchen.R;


public class GrocceriesFragment extends Fragment {
    RecyclerView grocceryListView;
    GrocceryListAdapter grocceryListAdapter;
    RecyclerView.LayoutManager layoutManager;
    String[] dataSet;
    Button positiveButton;
    EditText priceInput;
    EnterPriceDialogFragment inputDialog;


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
        inputDialog = new EnterPriceDialogFragment();
        inputDialog.setTitle(itemName);

        inputDialog.show(getActivity().getSupportFragmentManager(),
                "inputDialog");
        getActivity().getSupportFragmentManager().executePendingTransactions();

        //make button only be active when the input is >=0
        positiveButton = ((AlertDialog) inputDialog.getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setEnabled(false);
        //saves the price input field to add event listener to enable confirm button
        priceInput = inputDialog.getPrice();

        priceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s)){
                    positiveButton.setEnabled(false);
                    return;
                }
                try {
                    double priceValue = Double.parseDouble((priceInput.getText().toString()));
                    positiveButton.setEnabled(priceValue >= 0);
                } catch (NumberFormatException e) {
                    positiveButton.setEnabled(false);
                }
            }
        });
    }





}