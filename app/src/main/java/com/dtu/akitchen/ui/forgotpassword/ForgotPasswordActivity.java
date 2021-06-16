package com.dtu.akitchen.ui.forgotpassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dtu.akitchen.databinding.ActivityForgotPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private ForgotPasswordViewModel mViewModel;
    private FirebaseAuth auth;
    private String TAG = "ForgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        mViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        auth = FirebaseAuth.getInstance();
        final EditText usernameEditText = binding.username;
        final Button resetPasswordButton = binding.forgotButton;

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
                mViewModel.dataChanged(usernameEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);

        usernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    resetPassword(mViewModel.getEmail());
                }
                return false;
            }
        });

        resetPasswordButton.setOnClickListener(v -> {
            resetPassword(mViewModel.getEmail());
        });
    }

    private void resetPassword(String email){
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                    }
                });
        finish();
    }
}