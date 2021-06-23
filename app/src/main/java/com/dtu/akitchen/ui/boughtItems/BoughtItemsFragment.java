package com.dtu.akitchen.ui.boughtItems;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.dtu.akitchen.ShoppingListItems.BoughtItem;
import com.dtu.akitchen.authentication.LogInOut;

import com.dtu.akitchen.R;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.kitchen.Kitchen;
import com.dtu.akitchen.kitchen.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class BoughtItemsFragment extends Fragment {
    String userName;
    BoughtItemsAdapter boughtItemsAdapter;
    ArrayList<BoughtItem> boughtItemsList = new ArrayList<BoughtItem>();
    ValueEventListener valueEventListener;
    DatabaseReference reference;

    public BoughtItemsFragment() {
        // Required empty public constructor
    }


    public static BoughtItemsFragment newInstance() {
        BoughtItemsFragment fragment = new BoughtItemsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference()
                .child("kitchens").child(FirebaseCalls.kitchenId).child("bought_items");

        boughtItemsAdapter = new BoughtItemsAdapter(getActivity(), R.layout.bought_items_item ,boughtItemsList);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                boughtItemsList.clear();
                for (DataSnapshot dataSnapshot :snapshot.getChildren()) {
                    try {
                        String itemId = dataSnapshot.getKey();
                        String boughtById = dataSnapshot.child("bought_by").getValue(String.class);
                        double price = dataSnapshot.child("price").getValue(Double.class);
                        String name = dataSnapshot.child("itemName").getValue(String.class);
                        String date = dataSnapshot.child("date").getValue(String.class);

                        BoughtItem item = new BoughtItem(itemId, name, price, boughtById, date);
                        boughtItemsList.add(item);
                    } catch (NullPointerException e) {
                        Log.w("BoughtItems", "Item missing properties");
                    }
                }
                boughtItemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getActivity(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        };

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bought_items, container, false);
        ListView listView = (ListView) view.findViewById(R.id.bought_item_list);

        // connect to firebase
        listView.setAdapter(boughtItemsAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reference.addValueEventListener(valueEventListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        reference.removeEventListener(valueEventListener);
    }
}