package com.wasem.tower_administration.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityResetPasswordBinding;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityResetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }


    private void initializeView(){
        setOnClickListeners();
    }

    private void setOnClickListeners(){
        binding.btnSendReset.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_send_reset){
            performForgetPassword();
        }
    }

    private void performForgetPassword(){
        if (checkData()){
//            forgetPassword();
        }
    }

    private boolean checkData(){
        if (!binding.etResetMobile.getText().toString().trim().isEmpty()) {
            return true;
        }
        Toast.makeText(this, "Enter Mobile Number!", Toast.LENGTH_SHORT).show();
        return false;
    }



}