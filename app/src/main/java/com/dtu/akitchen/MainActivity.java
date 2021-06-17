package com.dtu.akitchen;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.dtu.akitchen.authentication.UserNotSignedInException;
import com.dtu.akitchen.authentication.logInOut;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.internal.view.SupportMenuItem;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.dtu.akitchen.ui.main.SectionsPagerAdapter;
import com.dtu.akitchen.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String TAG = "ClickedLogout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


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
                return true;
            case R.id.overview_submenu:
                Log.d(TAG, "Overview submenu clicked");
                return true;
            /*case R.id.Delete_User_SubMenu:
                Log.d(TAG, "Delete user submenu clicked");
                try {
                    logInOut.deleteUser();
                } catch (UserNotSignedInException e) {
                    e.printStackTrace();
                }
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}