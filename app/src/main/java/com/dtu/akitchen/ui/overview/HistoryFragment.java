package com.dtu.akitchen.ui.overview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtu.akitchen.R;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.kitchen.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    String TAG = "HistoryFragment";
    RecyclerView recyclerView;
    HistoryListAdapter historyListAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> tempCreatedDate;
    ArrayList<Long> tempUserCount;
    ArrayList<Double> tempTotalExpense;
    ArrayList<ArrayList<String>> tempNameData;
    ArrayList<ArrayList<Double>> tempValueData;

    ValueEventListener newDataListener;
    DatabaseReference hisRef;

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

        tempCreatedDate = new ArrayList<>();
        tempUserCount = new ArrayList<>();
        tempTotalExpense = new ArrayList<>();
        tempNameData = new ArrayList<>();
        tempValueData = new ArrayList<>();

        //Depreciated test values
        //createdDate = getResources().getStringArray(R.array.test_dates);
        //userCount = getResources().getIntArray(R.array.test_sizes);
        //totalExpense = getResources().getIntArray(R.array.test_sums);

        historyListAdapter = new HistoryListAdapter(tempCreatedDate, tempUserCount, tempTotalExpense, tempNameData, tempValueData, this);
        recyclerView.setAdapter(historyListAdapter);

        String kitchenId = FirebaseCalls.kitchenId;

        hisRef = FirebaseDatabase.getInstance().getReference()
                .child("kitchens").child(kitchenId);

        newDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                tempCreatedDate.clear();
                tempUserCount.clear();
                tempTotalExpense.clear();
                tempNameData.clear();
                tempValueData.clear();
                for (DataSnapshot snap: snapshot.child("summaries").child("history").getChildren()){
                    tempCreatedDate.add(snap.child("ended").getValue(String.class));
                    tempUserCount.add(snap.child("users").getChildrenCount());
                    tempTotalExpense.add(snap.child("total").getValue(Double.class));

                    ArrayList<String> nameData = new ArrayList<>();
                    ArrayList<Double> valueData = new ArrayList<>();
                    for (DataSnapshot undersnap : snap.child("users").getChildren()) {
                        valueData.add(undersnap.getValue(Double.class));
                        User user = FirebaseCalls.users.get(undersnap.getKey());
                        if (user != null && user.name != null) {
                            nameData.add(user.name);
                        } else {
                            nameData.add("Unnamed user");
                        }
                    }
                    tempNameData.add(nameData);
                    tempValueData.add(valueData);
                }
                historyListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.i(TAG,"VEL cancelled");
            }
        };

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        hisRef.addValueEventListener(newDataListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        hisRef.removeEventListener(newDataListener);
    }
}