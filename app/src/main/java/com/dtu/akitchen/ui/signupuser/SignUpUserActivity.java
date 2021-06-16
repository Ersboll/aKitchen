package com.dtu.akitchen.ui.signupuser;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dtu.akitchen.authentication.logInOut;
import com.dtu.akitchen.databinding.ActivitySignUpUserBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpUserActivity extends AppCompatActivity {
    private ActivitySignUpUserBinding binding;
    private SignUpVewModel mViewModel;
    private FirebaseAuth auth;
    private String TAG = "SignUpUserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        auth = FirebaseAuth.getInstance();

        mViewModel = new ViewModelProvider(this).get(SignUpVewModel.class);

        // Firebase
        final EditText SignUpMail = binding.signUpMail;
        final EditText SignUpPass = binding.signUpPass;
        final EditText SignUpPass2 = binding.signUpPass2;
        final Button SignUpButton = binding.SignUpButton;

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
                mViewModel.signUpDataChanged(SignUpMail.getText().toString(),SignUpPass.getText().toString(), SignUpPass2.getText().toString());
            }
        };

        SignUpMail.addTextChangedListener(afterTextChangedListener);
        SignUpPass.addTextChangedListener(afterTextChangedListener);
        SignUpPass2.addTextChangedListener(afterTextChangedListener);

        SignUpPass2.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE){
                if(mViewModel.isPasswordValid() && mViewModel.isUserNameValid()){
                    logInOut.signUp(this,mViewModel.getEmail(),mViewModel.getPassword());
                }
            }
            return false;
        });

        SignUpButton.setOnClickListener(v -> {
            Log.d(TAG, "You clicked SignUpButton");
            if(mViewModel.isPasswordValid() && mViewModel.isUserNameValid()){
                logInOut.signUp(this,mViewModel.getEmail(),mViewModel.getPassword());
            }

        });
    }
}