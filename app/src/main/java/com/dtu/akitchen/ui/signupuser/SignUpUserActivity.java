package com.dtu.akitchen.ui.signupuser;

import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.dtu.akitchen.R;
import com.dtu.akitchen.databinding.ActivitySignUpUserBinding;
import com.dtu.akitchen.ui.login_logout.LogOutActivity;
import com.dtu.akitchen.ui.login_logout.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

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
                    signUp(mViewModel.getEmail(),mViewModel.getPassword());
                }
            }
            return false;
        });

        SignUpButton.setOnClickListener(v -> {
            Log.d(TAG, "You clicked SignUpButton");
            if(mViewModel.isPasswordValid() && mViewModel.isUserNameValid()){
                signUp(mViewModel.getEmail(),mViewModel.getPassword());
            }

        });
    }


    public void signUp(String email, String password){
        Log.i(TAG, "Entered signUp");
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, task -> {
                    if (!task.isSuccessful()){
                        // Error if user could not be created
                        Toast.makeText(SignUpUserActivity.this, "ERROR creating user", Toast.LENGTH_LONG).show();
                        Log.w(TAG, "Error unable to create user");
                    } else { // Go to login screen, if sucessfull creating a new user.
                        Log.d(TAG, "Success creating user");
                        Intent intent = new Intent(SignUpUserActivity.this, LogOutActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}