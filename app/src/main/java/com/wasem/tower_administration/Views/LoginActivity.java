package com.wasem.tower_administration.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.tower_administration.Api.controllers.AuthApiController;
import com.wasem.tower_administration.Models.Admin;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityLoginBinding;
import com.wasem.tower_administration.interfaces.ProcessCallback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityLoginBinding binding;
    AuthApiController authApiController = new AuthApiController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    private void initializeView() {
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        binding.btnLogin.setOnClickListener(this);
        binding.tvPasswordForget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            performLogin();
        }
        else if (v.getId() == R.id.tv_password_forget) {
            Intent intent = new Intent(getApplicationContext(),ResetPasswordActivity.class);
            startActivity(intent);
        }
    }

    private void performLogin() {
        login();
    }

    private void login() {
        authApiController.login(getAdmin(), new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                FancyToast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private Admin getAdmin() {
        Admin admin = new Admin();
        admin.email = binding.etEmail.getText().toString().trim();
        admin.password = binding.etPassword.getText().toString().trim();
        return admin;
    }
}