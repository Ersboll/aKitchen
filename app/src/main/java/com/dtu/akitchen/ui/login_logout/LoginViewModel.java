package com.dtu.akitchen.ui.login_logout;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.ViewModel;

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

    public void loginDataChanged(String username, String password){
        this.email = isUserNameValid(username) ? username : "";
        this.password = isPasswordValid(password) ? password : "";
    }

    // Username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // Password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
