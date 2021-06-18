package com.dtu.akitchen;

import android.os.Bundle;

import com.dtu.akitchen.authentication.UserNotSignedInException;
import com.dtu.akitchen.authentication.logInOut;
import com.dtu.akitchen.databinding.ActivityMainBinding;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.dtu.akitchen.kitchen.Kitchen;
import com.dtu.akitchen.kitchen.User;
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

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String TAG = "ClickedLogout";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public TextView mTextviewTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;
        //Adding support for submenus
        MaterialToolbar toolbar =  binding.topAppBar;
        setSupportActionBar(toolbar);
        mTextviewTest = binding.TextViewTest;


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try { //TODO set name
                    User user = new User(logInOut.getCurrentUser().getUid(),true, "");
                    Kitchen kitchen = new Kitchen("Vores køkken", user);

                    FirebaseCalls.createKitchen(kitchen, user);

                } catch (UserNotSignedInException e) {
                    e.printStackTrace();
                }
                Snackbar.make(view, "Created a new kitchen", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


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
            case R.id.Logout_SubMenu:
                Log.d(TAG,"Logout submenu clicked");
                logInOut.logout();
                return true;
            case R.id.Delete_User_SubMenu:
                Log.d(TAG, "Delete user submenu clicked");
                try {
                    logInOut.deleteUser();
                } catch (UserNotSignedInException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}