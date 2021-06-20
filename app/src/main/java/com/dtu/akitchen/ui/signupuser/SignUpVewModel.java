package com.dtu.akitchen.ui.signupuser;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.dtu.akitchen.authentication.LogInOut;

public class SignUpVewModel extends ViewModel {
    private String email;
    private String password;
    private String password2;
    private String TAG = "SignUpViewModel";

    public SignUpVewModel() { Log.i(TAG, "ViewModel is created"); }



    @Override
    protected void onCleared(){
        super.onCleared();
        Log.i(TAG, "ViewModel is Destroyed");
    }

    public void signUpDataChanged(String username, String password, String password2){
        this.email = username;
        this.password = password;
        this.password2 = password2;
    }

    public boolean isUserNameValid(String username){
        return LogInOut.isEmailValid(username);
    }

    public boolean arePasswordsValid(String password, String password2){
        return LogInOut.arePasswordsValid(password,password2);
    }

    public boolean isPasswordValid(){
        return LogInOut.arePasswordsValid(password,password);
    }

    public boolean isUserNameValid(){
        return isUserNameValid(email);
    }



    //Getters - Setters
    public String getPassword() { return password; }

    public String getEmail() { return email; }


}
