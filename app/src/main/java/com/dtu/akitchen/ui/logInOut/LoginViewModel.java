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

    public void loginDataChanged(String username, String password){
        this.email = isUserNameValid(username) ? username : "";
        this.password = isPasswordValid(password) ? password : "";
    }

    // Username validation check
    private boolean isUserNameValid(String username) {
        return LogInOut.isEmailValid(username);
    }

    public boolean isUserNameValid(){
        return isUserNameValid(email);
    }

    // Password validation check
    public boolean isPasswordValid(String password) {
        return LogInOut.isPasswordValid(password);
    }

    public boolean isPasswordValid(){
        return isPasswordValid(password);
    }


    // getters

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
