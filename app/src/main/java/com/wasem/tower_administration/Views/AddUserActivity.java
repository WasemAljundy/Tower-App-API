package com.wasem.tower_administration.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wasem.tower_administration.databinding.ActivityAddUserBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class AddUserActivity extends AppCompatActivity {
    ActivityAddUserBinding binding;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupView();

        binding.etUserDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                binding.etUserDate.setText(date);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                dpd.show(getSupportFragmentManager(), "DatePickerDialog");
            }
        });
    }

    private void setupView(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("user_id",0);
        String title =  intent.getStringExtra("user_update_title");
        Log.d("USER-ID", "setupView: "+id);
        if ( id != 0 && title != null) {
            binding.tvAddUserWelcome.setText(title);
        }
    }
}