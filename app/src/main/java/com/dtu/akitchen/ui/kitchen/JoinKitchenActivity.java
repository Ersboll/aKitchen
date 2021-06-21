package com.dtu.akitchen.ui.kitchen;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.databinding.ActivityJoinKitchenBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class JoinKitchenActivity extends AppCompatActivity {

    private final String TAG = "JoinKitchen";
    private ActivityJoinKitchenBinding binding;
    private JoinKitchenViewModel mViewModel;
    private DatabaseReference mDatabase;
    private boolean kitchenExists = false;
    private ValueEventListener kitchenListener;
    DatabaseReference kitchenIDReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityJoinKitchenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mViewModel = new ViewModelProvider(this).get(JoinKitchenViewModel.class);

        final EditText kitchenInviteCodeEditText = binding.kitchenInviteCodeEditText;
        final Button joinWithCodeButton = binding.joinWithCodeButton;
        //final Button joinWithQRButton = binding.joinWithQRButton;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        kitchenIDReference = mDatabase.child("kitchens").child(mViewModel.getInviteCode());

        kitchenListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                kitchenExists = snapshot.exists();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w(TAG, "Warning could not retrive data: " + error.getMessage());
            }
        };


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignored
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignored
            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.dataChanged(kitchenInviteCodeEditText.getText().toString());
                kitchenIDReference.removeEventListener(kitchenListener);
                DatabaseReference kitchenIDReference = mDatabase.child("kitchens").child(mViewModel.getInviteCode());
                kitchenIDReference.addListenerForSingleValueEvent(kitchenListener);
            }
        };

        kitchenInviteCodeEditText.addTextChangedListener(afterTextChangedListener);
        kitchenInviteCodeEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                joinKitchen(mViewModel.getInviteCode());
            }
            return false;
        });

        joinWithCodeButton.setOnClickListener(v -> joinKitchen(mViewModel.getInviteCode()));

    }

    @Override
    protected void onPause() {
        super.onPause();

        kitchenIDReference.removeEventListener(kitchenListener);
    }

    private void joinKitchen(String inviteCode) {
        FirebaseUser user = LogInOut.getCurrentUser();

        if (kitchenExists) {
            mDatabase.child("users")
                    .child(user.getUid())
                    .child("kitchen")
                    .setValue(inviteCode);

            mDatabase.child("kitchens")
                    .child(inviteCode)
                    .child("users")
                    .child(user.getUid())
                    .child("admin")
                    .setValue(false);
            mDatabase.child("kitchens")
                    .child(inviteCode)
                    .child("users")
                    .child(user.getUid())
                    .child("active")
                    .setValue(true);
        } else {
            Toast.makeText(this, "The kitchen you are trying to join doesn't exist", Toast.LENGTH_SHORT).show();
        }
    }
}