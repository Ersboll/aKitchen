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

import java.math.BigDecimal;
import java.util.ArrayList;


public class ShoppingListFragment extends Fragment {
    RecyclerView recyclerView;
    ShoppingListAdapter shoppingListAdapter;
    RecyclerView.LayoutManager layoutManager;
    EditText priceInput;
    EnterPriceDialogFragment inputDialog;
    DAOshoppingListItems shoppingListItemsDAO;
    ArrayList<ShoppingListItem> shoppingItems = new ArrayList<ShoppingListItem>();
    DatabaseReference shoppingListReference;
    ValueEventListener valueEventListener;
    Button addButton;
    View.OnClickListener addButtonListener;




    public static ShoppingListFragment newInstance() {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shoppingListAdapter = new ShoppingListAdapter(shoppingItems, this);

        //get database reference to the shopping list
        String kitchenId = FirebaseCalls.kitchenId;

        shoppingListReference = FirebaseDatabase.getInstance().getReference()
                .child("kitchens").child(kitchenId).child("shopping_list");

        //listens to changes in database, and updates recyclerview accordingly
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                shoppingItems.clear();
                ShoppingListItem shoppingListItem;
                for (DataSnapshot dataSnapshot :snapshot.getChildren()) {
                    shoppingListItem = new ShoppingListItem(dataSnapshot.getKey(),
                            dataSnapshot.getValue().toString());
                    shoppingItems.add(shoppingListItem);
                }
                shoppingListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                showShortToast("Failed to get data");
            }

        };


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

        //set custom made adapter for groccery items
        recyclerView.setAdapter(shoppingListAdapter);
        TextView itemTextView = (TextView) rootView.findViewById(R.id.new_item_text);

        //set add button onClick
        addButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newItemText = itemTextView.getText().toString();
                Log.i("ADD", newItemText);
                shoppingListItemsDAO = new DAOshoppingListItems();

                shoppingListItemsDAO.addItem(newItemText).addOnSuccessListener(suc -> {
                    showShortToast(newItemText + " added");
                }).addOnFailureListener( err -> {
                    showShortToast(err.getMessage());
                }).addOnCompleteListener( com -> {
                    itemTextView.setText("");
                });

            }
        };

        addButton = rootView.findViewById((R.id.add_item_button));
        addButton.setOnClickListener(addButtonListener);


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

    }

    @Override
    public void onResume() {
        super.onResume();
        shoppingListReference.addValueEventListener(valueEventListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        shoppingListReference.removeEventListener(valueEventListener);

    }



}