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

import java.util.ArrayList;

public class CurrentFragment extends Fragment {
    String TAG = "CurrentFragment";
    RecyclerView recyclerView;
    CurrentListAdapter currentListAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> tempNameData;
    ArrayList<Double> tempValueData;

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

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tempValueData = new ArrayList<>();
        tempNameData = new ArrayList<>();
        tempValueData.add(Double.parseDouble("0"));
        tempNameData.add("");

        //tempNameData = getResources().getStringArray(R.array.test_names);
        //tempValueData = getResources().getIntArray(R.array.test_values);
        currentListAdapter = new CurrentListAdapter(tempNameData, tempValueData, this);
        recyclerView.setAdapter(currentListAdapter);

        String kitchenId = FirebaseCalls.kitchenId;

        DatabaseReference summaryRef = FirebaseDatabase.getInstance().getReference()
                .child("kitchens").child(kitchenId).child("summaries").child("current");

        summaryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                tempNameData.clear();
                tempValueData.clear();
                for (DataSnapshot snap: snapshot.child("users").getChildren()) {
                    tempValueData.add(snap.getValue(Double.class));
                    User user = FirebaseCalls.users.get(snap.getKey());
                    if (user != null && user.name != null) {
                        tempNameData.add(user.name);
                    } else {
                        tempNameData.add("Unnamed user");
                    }

                    Log.i(TAG,tempValueData.get(tempValueData.size()-1).toString());
                }
                currentListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.i(TAG,"VEL cancelled");
            }
        });

        Button concludeButton = root.findViewById(R.id.conclude_button);
        concludeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Create summary of current user debts, send to history list and restart tally
                //TODO: Firebase integration
                if(true){
                    Toast.makeText(getContext(),R.string.settle_accounts_success, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),R.string.settle_accounts_fail, Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }
}