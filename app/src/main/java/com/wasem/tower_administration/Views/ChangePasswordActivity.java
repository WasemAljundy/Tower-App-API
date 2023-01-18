package com.wasem.tower_administration.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.tower_administration.Api.controllers.AuthApiController;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityChangePasswordBinding;
import com.wasem.tower_administration.interfaces.ProcessCallback;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityChangePasswordBinding binding;
    AuthApiController authApiController = new AuthApiController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    private void initializeView(){
        setOnClickListeners();
    }

    private void setOnClickListeners(){
        binding.btnSaveChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save_change_password) {
            performResetPassword();
        }
    }

    private void performResetPassword(){
        if (checkPasswordMatch()){
            changePassword();
        }
    }

    private boolean checkPasswordMatch(){
        if (binding.etNewPassword.getText().toString().trim().equals(binding.etNewConfirmPassword.getText().toString().trim())) {
            return true;
        }
        FancyToast.makeText(this,"Passwords didn't match!", Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
        return false;
    }

    private void changePassword() {
        authApiController.changePassword(binding.etCurrentPassword.getText().toString(), binding.etNewPassword.getText().toString(), binding.etNewConfirmPassword.getText().toString(), new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                FancyToast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
            }

            @Override
            public void onFailure(String message) {
                FancyToast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            }
        });
    }
}