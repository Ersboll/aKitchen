package com.dtu.akitchen.ui.login_logout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dtu.akitchen.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                mViewModel.loginDataChanged(usernameEditText.getText().toString(),passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    login(mViewModel.getEmail(),mViewModel.getPassword());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(v -> {
            login(mViewModel.getEmail(),mViewModel.getPassword());
        });
    }


    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener( this, task -> {
                    if (task.isSuccessful()){
                        // Sign in succes, update UI with the signed-in user's information
                        FirebaseUser user = auth.getCurrentUser();
                        //Toast.makeText(LoginActivity.this, user.getEmail(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, LogOutActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails displays a message to the user
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}