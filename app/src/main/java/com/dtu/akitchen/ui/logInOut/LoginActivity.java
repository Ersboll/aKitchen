package com.dtu.akitchen.ui.logInOut;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.databinding.ActivityLoginBinding;
import com.dtu.akitchen.ui.forgotpassword.ForgotPasswordActivity;
import com.dtu.akitchen.ui.signupuser.SignUpUserActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Author: Niels Kjær Ersbøll
 * s183903
 */
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel mViewModel;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        auth = FirebaseAuth.getInstance();

        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button signUpButton = binding.signUp;
        final Button forgotPasswordButton = binding.forgotButton;

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
                mViewModel.loginDataChanged(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                logIn();
            }
            return false;
        });

        loginButton.setOnClickListener(v -> logIn());

        forgotPasswordButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpUserActivity.class);
            startActivity(intent);
        });
    }

    public void logIn() {
        if (!mViewModel.isEmailValid()) {
            Toast.makeText(this,"Must be a vaild email address", Toast.LENGTH_SHORT).show();
        } else if(!mViewModel.isPasswordValid()){
            Toast.makeText(this, "You must write your password", Toast.LENGTH_SHORT).show();
        } else {
            LogInOut.login(this, mViewModel.getEmail(), mViewModel.getPassword());
        }
    }

}