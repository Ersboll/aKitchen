package com.dtu.akitchen.ui.kitchen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.dtu.akitchen.R;
import com.dtu.akitchen.databinding.ActivityInviteBinding;
import com.dtu.akitchen.kitchen.FirebaseCalls;

public class InviteActivity extends AppCompatActivity {

    private ActivityInviteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInviteBinding.inflate(getLayoutInflater());
        binding.inviteCode.setText(FirebaseCalls.kitchenId);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(binding.getRoot());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickText (View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("invite code", binding.inviteCode.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, R.string.copied_invite_code, Toast.LENGTH_SHORT).show();
    }
}
