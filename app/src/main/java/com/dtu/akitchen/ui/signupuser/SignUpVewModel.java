package com.dtu.akitchen.ui.signupuser;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.ViewModel;

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
        this.email = isUserNameValid(username) ? username : "";
        this.password = isPasswordValid(password,password2) ? password: "";
    }

    public boolean isUserNameValid(String username){
        if (username == null){
            return false;
        } if (username.contains("@")){
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    public boolean isPasswordValid(String password, String password2){
        if(password == null || password2 == null){
            return false;
        }
        if (password.isEmpty() || password2.isEmpty()) {
            return false;
        } if (!password.equals(password2)){
            return false;
        } if (password.equals(password2) && password.trim().length() < 5){
            return false;
        } else{
            return true;
        }
    }

    public boolean isPasswordValid(){
        return isPasswordValid(password,password);
    }

    public boolean isUserNameValid(){
        return isUserNameValid(email);
    }



    //Getters - Setters
    public String getPassword() { return password; }

    public String getEmail() { return email; }


}
