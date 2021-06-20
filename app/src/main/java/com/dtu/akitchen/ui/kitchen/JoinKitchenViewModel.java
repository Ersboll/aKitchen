package com.dtu.akitchen.ui.kitchen;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class JoinKitchenViewModel extends ViewModel {
    private final String TAG = "JoinKitchen";
    private String inviteCode;

    public JoinKitchenViewModel(){
        Log.i(TAG,"JoinKitchen Viewmodel created");
        inviteCode="";
    }

    public void dataChanged(String inviteCode){
        this.inviteCode = inviteCode;
    }

    public String getInviteCode() {
        return inviteCode;
    }
}
