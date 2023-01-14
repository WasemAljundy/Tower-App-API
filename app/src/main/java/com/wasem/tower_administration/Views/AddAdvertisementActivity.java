package com.wasem.tower_administration.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wasem.tower_administration.databinding.ActivityAddAdvertisementBinding;

public class AddAdvertisementActivity extends AppCompatActivity {
    ActivityAddAdvertisementBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAdvertisementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}