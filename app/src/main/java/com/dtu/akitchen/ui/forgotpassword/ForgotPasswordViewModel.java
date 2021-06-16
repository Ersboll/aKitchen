package com.dtu.akitchen.ui.forgotpassword;

import android.util.Patterns;

import androidx.lifecycle.ViewModel;

public class ForgotPasswordViewModel extends ViewModel {
    private String email;

    public ForgotPasswordViewModel(){}

    public void dataChanged(String email){
        this.email = isEmailValid(email) ? email : "";
    }

    private boolean isEmailValid(String email){
        if(email == null){
            return false;
        }
        if(email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return !email.trim().isEmpty();
        }
    }

    public String getEmail() {
        return email;
    }
}
