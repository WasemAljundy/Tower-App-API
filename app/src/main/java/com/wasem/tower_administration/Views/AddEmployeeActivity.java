package com.wasem.tower_administration.Views;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.tower_administration.Api.controllers.EmployeeApiController;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityAddEmployeeBinding;
import com.wasem.tower_administration.interfaces.ProcessCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddEmployeeActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAddEmployeeBinding binding;
    EmployeeApiController employeeApiController = new EmployeeApiController();
    private ActivityResultLauncher<String> permissionResultLauncher;
    private ActivityResultLauncher<Void> cameraResultLauncher;
    private Bitmap imageBitmap;
    private MultipartBody.Part file;
    private int emp_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupView();

        initializeView();
    }

    private void setupView(){
        Intent intent = getIntent();
        emp_id = intent.getIntExtra("emp_id",0);
        String title =  intent.getStringExtra("emp_update_title");
        if ( emp_id != 0 && title != null) {
            binding.tvAddEmployeeWelcome.setText(title);
            binding.btnAddEmployee.setVisibility(View.GONE);
            binding.btnUpdateEmployee.setVisibility(View.VISIBLE);
        }
    }

    private void initializeView() {
        setupResultsLauncher();
        onClickListeners();
    }

    private void onClickListeners() {
        binding.imgEmployee.setOnClickListener(this);
        binding.btnAddEmployee.setOnClickListener(this);
        binding.btnUpdateEmployee.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_employee) {
            performImageUpload();
            addEmployee();
        }
        else if (view.getId() == R.id.btn_update_employee) {
            performImageUpload();
            updateEmployee();
        }
        else if (view.getId() == R.id.img_employee) {
            pickImage();
        }
    }

    private void addEmployee() {
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etEmployeeFullname.getText().toString());
        RequestBody mobile = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etEmployeeMobileNumber.getText().toString());
        RequestBody nationalNumber = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etEmployeeNationalIdNumber.getText().toString());
        employeeApiController.addEmployee(name, mobile, nationalNumber, file, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                FancyToast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
            }

            @Override
            public void onFailure(String message) {
                FancyToast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            }
        });
    }

    private void updateEmployee() {
        RequestBody bodyMethod = RequestBody.create(MediaType.parse("multipart/form-data"), "PUT");
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etEmployeeFullname.getText().toString());
        RequestBody mobile = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etEmployeeMobileNumber.getText().toString());
        RequestBody nationalNumber = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etEmployeeNationalIdNumber.getText().toString());
        employeeApiController.updateEmployee(emp_id, name, mobile, nationalNumber, file, bodyMethod, new ProcessCallback() {
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

    private void pickImage() {
        permissionResultLauncher.launch(Manifest.permission.CAMERA);
    }

    private void setupResultsLauncher() {
        permissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result) {
                    cameraResultLauncher.launch(null);
                }
            }
        });

        cameraResultLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    imageBitmap = result;
                    binding.imgEmployee.setImageBitmap(imageBitmap);
                }
            }
        });
    }

    private void performImageUpload() {
        if (imageBitmap != null) {
            uploadImage();
        }
    }

    private void uploadImage() {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), bitmapToBytes());
        file = MultipartBody.Part.createFormData("image", "image-file", requestBody);
    }

    private byte[] bitmapToBytes() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}