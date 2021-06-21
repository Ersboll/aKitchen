package com.dtu.akitchen;

import android.content.Intent;
import android.os.Bundle;

import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.databinding.ActivityMainBinding;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.kitchen.Kitchen;
import com.dtu.akitchen.kitchen.User;
import com.dtu.akitchen.ui.kitchen.InviteActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.TextView;

import com.dtu.akitchen.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.dtu.akitchen.ui.main.SettingsActivity;
import com.dtu.akitchen.ui.overview.OverviewActivity;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String TAG = "ClickedLogout";
    private FirebaseDatabase database;
    public TextView mTextviewTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        //Adding support for submenus
        MaterialToolbar toolbar =  binding.topAppBar;
        setSupportActionBar(toolbar);


    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = FirebaseCalls.users.get(LogInOut.getCurrentUser().getUid());
        if((user != null ? user.name : null) == null || user.name.isEmpty()){
            // if a display name is not attached to the user
            // Create a dialog to set it

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Display name");
            builder.setIcon(R.drawable.ic_baseline_account_circle_24);
            builder.setMessage("Enter your display name for this kitchen");

            EditText alertInput = new EditText(this);

            builder.setView(alertInput);

            builder.setPositiveButton("Set", (dialog, which) -> {
               String displayName = alertInput.getText().toString();
               FirebaseDatabase.getInstance()
                       .getReference("/kitchens/" + FirebaseCalls.kitchenId + "/users/" + LogInOut.getCurrentUser().getUid() + "/name")
                       .setValue(displayName);
            });

            AlertDialog ad = builder.create();
            ad.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mini_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout_submenu:
                Log.d(TAG,"Logout submenu clicked");
                LogInOut.logout();
                return true;
            case R.id.settings_submenu:
                Log.d(TAG, "Settings submenu clicked");
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            case R.id.invite_submenu:
                Log.d(TAG, "Invite submenu clicked");
                startActivity(new Intent(MainActivity.this, InviteActivity.class));
                return true;
            case R.id.overview_submenu:
                Log.d(TAG, "Overview submenu clicked");
                Intent intentOverview = new Intent(this, OverviewActivity.class);
                startActivity(intentOverview);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}