package com.wasem.tower_administration.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.tower_administration.Api.controllers.AuthApiController;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityResetPasswordBinding;
import com.wasem.tower_administration.interfaces.ProcessCallback;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityResetPasswordBinding binding;
    AuthApiController authApiController = new AuthApiController();
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
        binding.btnSaveResetPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_send_reset){
            performForgetPassword();
            setupResetPasswordView();
        }
        else if (view.getId() == R.id.btn_save_reset_password) {
            performResetPassword();
        }
    }

    private void setupResetPasswordView() {
        binding.btnSendReset.setVisibility(View.GONE);
        binding.tvCodeTxt.setVisibility(View.VISIBLE);
        binding.etCode.setVisibility(View.VISIBLE);
        binding.tvPasswordTxt.setVisibility(View.VISIBLE);
        binding.etNewPassword.setVisibility(View.VISIBLE);
        binding.etNewPasswordConfirm.setVisibility(View.VISIBLE);
        binding.btnSaveResetPassword.setVisibility(View.VISIBLE);
    }

    private void performForgetPassword(){
        if (checkEmail()){
            forgetPassword();
        }
    }

    private void performResetPassword(){
        if (checkPasswordMatch()){
            resetPassword();
        }
    }

    private boolean checkEmail(){
        if (!binding.etResetEmail.getText().toString().trim().isEmpty()) {
            return true;
        }
        FancyToast.makeText(this,"Enter Valid Email !",Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
        return false;
    }

    private boolean checkPasswordMatch(){
        if (binding.etNewPassword.getText().toString().trim().equals(binding.etNewPasswordConfirm.getText().toString().trim())) {
            return true;
        }
        FancyToast.makeText(this,"Passwords didn't match!",Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
        return false;
    }

    private void forgetPassword() {
        authApiController.forgetPassword(binding.etResetEmail.getText().toString(), new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                FancyToast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();
            }

            @Override
            public void onFailure(String message) {
                FancyToast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            }
        });
    }

    private void resetPassword() {
        authApiController.resetPassword(binding.etResetEmail.getText().toString(), binding.etCode.getText().toString(), binding.etNewPassword.getText().toString(), binding.etNewPasswordConfirm.getText().toString(), new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                FancyToast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
            }

            @Override
            public void onFailure(String message) {
                FancyToast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            }
        });
    }



}