package com.dtu.akitchen;

import android.content.Intent;
import android.os.Bundle;

import com.dtu.akitchen.authentication.UserNotSignedInException;
import com.dtu.akitchen.authentication.logInOut;
import com.dtu.akitchen.databinding.ActivityMainBinding;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.kitchen.Kitchen;
import com.dtu.akitchen.kitchen.User;
import com.dtu.akitchen.ui.kitchen.InviteActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.TextView;

import com.dtu.akitchen.ui.main.SectionsPagerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.dtu.akitchen.authentication.logInOut;
import com.dtu.akitchen.databinding.ActivityMainBinding;
import com.dtu.akitchen.ui.main.SectionsPagerAdapter;
import com.dtu.akitchen.ui.main.SettingsActivity;
import com.dtu.akitchen.ui.overview.OverviewActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;


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
                logInOut.logout();
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