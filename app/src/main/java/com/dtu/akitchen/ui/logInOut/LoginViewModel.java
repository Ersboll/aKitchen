package com.dtu.akitchen.ui.logInOut;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.dtu.akitchen.authentication.LogInOut;

public class LoginViewModel extends ViewModel {
    private String email;
    private String password;
    private String TAG = "LoginViewModel";


    public LoginViewModel() {
        Log.i(TAG, "ViewModel is Created");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel is Destroyed");
    }

    public void loginDataChanged(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isEmailValid() {
        return LogInOut.isEmailValid(email);
    }

    public boolean isPasswordValid() {
        return LogInOut.isPasswordValid(password);
    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
