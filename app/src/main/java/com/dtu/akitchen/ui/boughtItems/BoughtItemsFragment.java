package com.dtu.akitchen.ui.boughtItems;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dtu.akitchen.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class BoughtItemsFragment extends Fragment {

    BoughtItemsAdapter boughtItemsAdapter;
    ArrayList<BoughtItem> boughtItemsList = new ArrayList<BoughtItem>();

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

        boughtItemsAdapter = new BoughtItemsAdapter(getActivity(), R.layout.bought_items_item ,boughtItemsList);

        // connect to firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("bought_items").child("kitchenId");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()) {
                    String bought_by = (String) dataSnapshot.child("bought_by").getValue();
                    double price = (double)  dataSnapshot.child("price").getValue();
                    String name = (String) dataSnapshot.child("itemName").getValue();
                    String date = (String) dataSnapshot.child("date").getValue();

                    BoughtItem item = new BoughtItem(name, price, bought_by, date);
                    boughtItemsList.add(item);
                    boughtItemsAdapter.notifyDataSetChanged();
                    System.out.println(item.name);
                    System.out.println(item.bought_by);
                }
                System.out.println(boughtItemsList.size());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bought_items, container, false);
        ListView listView = (ListView) view.findViewById(R.id.bought_item_list);

        System.out.println("LIST SIZE: "+boughtItemsList.size());
        listView.setAdapter(boughtItemsAdapter);
        return view;
    }
}