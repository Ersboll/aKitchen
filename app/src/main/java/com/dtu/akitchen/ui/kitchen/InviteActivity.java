package com.dtu.akitchen.ui.kitchen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dtu.akitchen.databinding.ActivityInviteBinding;

public class InviteActivity extends AppCompatActivity {

    private ActivityInviteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInviteBinding.inflate(getLayoutInflater());
        binding.inviteCode.setText("Hello world!");

        setContentView(binding.getRoot());
    }
}
