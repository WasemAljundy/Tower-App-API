package com.wasem.tower_administration.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.wasem.tower_administration.databinding.ActivityAddEmployeeBinding;

public class AddEmployeeActivity extends AppCompatActivity {
    ActivityAddEmployeeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private void setupView(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("emp_id",0);
        String title =  intent.getStringExtra("emp_update_title");
        Log.d("EMPLOYEE-ID", "setupView: "+id);
        if ( id != 0 && title != null) {
            binding.tvAddEmployeeWelcome.setText(title);
        }
    }
}