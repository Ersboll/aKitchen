package com.dtu.akitchen.ShoppingListItems;
//Philip Hviid
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import android.widget.Toast;

import com.dtu.akitchen.R;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.ui.main.EnterPriceDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ShoppingListFragment extends Fragment {
    RecyclerView recyclerView;
    ShoppingListAdapter shoppingListAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button positiveButton;
    EditText priceInput;
    EnterPriceDialogFragment inputDialog;
    DAOshoppingListItems shoppingListItemsDAO;
    ArrayList<ShoppingListItem> shoppingItems = new ArrayList<ShoppingListItem>();




    public static ShoppingListFragment newInstance() {
        ShoppingListFragment fragment = new ShoppingListFragment();
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
        recyclerView = rootView.findViewById(R.id.grocceries_list_view);

        //set layoutmanager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //seperation lines for styling
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //get database reference to the shopping list
        String kitchenId = FirebaseCalls.kitchenId;

        Log.i("KitchenId",kitchenId);
        DatabaseReference shoppingListReference = FirebaseDatabase.getInstance().getReference()
                .child("kitchens").child(kitchenId).child("shopping_list");

        //set custom made adapter for groccery items
        shoppingListAdapter = new ShoppingListAdapter(shoppingItems, this);
        recyclerView.setAdapter(shoppingListAdapter);


        shoppingListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                shoppingItems.clear();
                ShoppingListItem shoppingListItem;
                for (DataSnapshot dataSnapshot :snapshot.getChildren()) {
                    shoppingListItem = new ShoppingListItem(dataSnapshot.getKey(),
                            dataSnapshot.getValue().toString());
                    shoppingItems.add(shoppingListItem);
                    shoppingListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                showShortToast("Failed to get data");
            }
        });


        //set add button onClick

        Button addButton = rootView.findViewById((R.id.add_item_button));
        //only pressable if not blank

        TextView itemTextView = (TextView) rootView.findViewById(R.id.new_item_text);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newItemText = itemTextView.getText().toString();
                Log.i("ADD", newItemText);
                shoppingListItemsDAO = new DAOshoppingListItems();

                shoppingListItemsDAO.addItem(newItemText).addOnSuccessListener(suc -> {
                    showShortToast(newItemText + " added");
                    itemTextView.setText("");
                }).addOnFailureListener( err -> {
                    showShortToast(err.getMessage());
                });

            }
        });

        //addButton only works if an item has been entered
        addButton.setEnabled(false);
        itemTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!itemTextView.getText().toString().isEmpty()){
                    addButton.setEnabled(true);
                } else {
                    addButton.setEnabled(false);
                }
            }
        });


        //return the inflated flagment
        return rootView;
    }





    public void showShortToast(String message) {
        Toast.makeText(getContext(), message , Toast.LENGTH_SHORT).show();
    }

    public void openInputPriceDialog(String itemName, String itemKey) {
        inputDialog = new EnterPriceDialogFragment();
        inputDialog.setContext(getContext());
        inputDialog.setTitle(itemName);
        inputDialog.setItemKey(itemKey);

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
                    //to preven huge numbers from crashing database
                    positiveButton.setEnabled(priceValue >= 0 && priceValue <= 10000);
                } catch (NumberFormatException e) {
                    positiveButton.setEnabled(false);
                }
            }
        });
    }



}