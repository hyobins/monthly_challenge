package com.example.monthly_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import Login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread(new Runnable() {
            @Override
            public void run() {
                {
                    try{
                        Thread.sleep(3000);
                    } catch (Exception e){
                    }
                    finish();
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, Login.LoginTypeActivity.class);
                    startActivity(intent);
                }
            }
        }).start();
    }
}