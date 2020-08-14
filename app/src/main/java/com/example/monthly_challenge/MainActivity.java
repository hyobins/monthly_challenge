package com.example.monthly_challenge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.monthly_challenge.BottomNavigationFragment.*;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.monthly_challenge.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    Context context = this;
    private HomeFragment homeFragment;
    private MenuFragment menuFragment;
    private AlarmFragment alarmFragment;
    private SettingFragment settingFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
        homeFragment = new HomeFragment();
        menuFragment = new MenuFragment();
        alarmFragment = new AlarmFragment();
        settingFragment = new SettingFragment();
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout, homeFragment).commitAllowingStateLoss();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        transaction = fragmentManager.beginTransaction();
        switch (item.getItemId()){
            case R.id.navigation_home:
                transaction.replace(R.id.main_layout, homeFragment).commitAllowingStateLoss();
                return true;
            case R.id.navigation_menu:
                transaction.replace(R.id.main_layout, menuFragment).commitAllowingStateLoss();
                return true;
            case R.id.navigation_alarm:
                transaction.replace(R.id.main_layout, alarmFragment).commitAllowingStateLoss();
                return true;
            case R.id.navigation_setting:
                transaction.replace(R.id.main_layout, settingFragment).commitAllowingStateLoss();
                return true;
        }
        return false;
    }
}