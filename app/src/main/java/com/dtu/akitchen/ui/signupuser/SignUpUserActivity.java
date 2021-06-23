package com.dtu.akitchen.ui.signupuser;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.databinding.ActivitySignUpUserBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpUserActivity extends AppCompatActivity {
    private ActivitySignUpUserBinding binding;
    private SignUpUserVewModel mViewModel;
    private FirebaseAuth auth;
    private String TAG = "SignUpUserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(view);

        auth = FirebaseAuth.getInstance();

        mViewModel = new ViewModelProvider(this).get(SignUpUserVewModel.class);

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
                signUp();
            }
            return false;
        });

        SignUpButton.setOnClickListener(v -> {
            signUp();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void signUp() {
        if( !mViewModel.isEmailValid() ){
            Toast.makeText(this,"Must be a vaild email address", Toast.LENGTH_SHORT).show();
        } else if(!mViewModel.doPasswordsMatch()){
            Toast.makeText(this, "The 2 passwords must match", Toast.LENGTH_SHORT).show();
        } else if(!mViewModel.isPasswordValid()){
            Toast.makeText(this, "Password must be at least 6 characters long",Toast.LENGTH_LONG).show();
        } else {
            LogInOut.signUp(SignUpUserActivity.this, mViewModel.getEmail(), mViewModel.getPassword());

        }
    }
}