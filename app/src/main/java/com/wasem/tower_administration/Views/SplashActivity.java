package com.wasem.tower_administration.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wasem.tower_administration.databinding.ActivitySplashBinding;
import com.wasem.tower_administration.prefs.AppSharedPreferences;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding activitySplashBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(activitySplashBinding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        controlSplashActivity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void controlSplashActivity() {
        //3000ms - 3s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLoggedIn = AppSharedPreferences.getInstance().isLoggedIn();
                Intent intent = new Intent(getApplicationContext(), isLoggedIn ? MainActivity.class : LoginActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }
}