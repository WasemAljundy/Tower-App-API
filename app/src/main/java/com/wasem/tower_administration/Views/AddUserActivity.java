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
import com.wasem.tower_administration.Api.controllers.UserApiController;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityAddUserBinding;
import com.wasem.tower_administration.interfaces.ProcessCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAddUserBinding binding;
    UserApiController userApiController = new UserApiController();
    private ActivityResultLauncher<String> permissionResultLauncher;
    private ActivityResultLauncher<Void> cameraResultLauncher;
    private Bitmap imageBitmap;
    private MultipartBody.Part file;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupView();

        initializeView();
    }

    private void setupView(){
        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id",0);
        String title =  intent.getStringExtra("user_update_title");
        if ( user_id != 0 && title != null) {
            binding.tvAddUserWelcome.setText(title);
            binding.btnAddUser.setVisibility(View.GONE);
            binding.btnUpdateUser.setVisibility(View.VISIBLE);
        }
    }

    private void initializeView() {
        setupResultsLauncher();
        onClickListeners();
    }

    private void onClickListeners() {
        binding.etUserDate.setOnClickListener(this);
        binding.imgUser.setOnClickListener(this);
        binding.btnAddUser.setOnClickListener(this);
        binding.btnUpdateUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_user) {
            performImageUpload();
            addUser();
        }
        else if (view.getId() == R.id.btn_update_user) {
            performImageUpload();
            updateUser();
        }
        else if (view.getId() == R.id.img_user) {
            pickImage();
        }
        else if (view.getId() == R.id.et_user_date) {
            getSelectedDate();
        }
    }

    private void getSelectedDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        now.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
                        String date = dateFormat.format(now.getTime());
                        binding.etUserDate.setText(date);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        dpd.show(getSupportFragmentManager(), "DatePickerDialog");
    }

    private void addUser() {
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etUserFullname.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etUserEmail.getText().toString());
        RequestBody mobile = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etUserMobileNumber.getText().toString());
        RequestBody nationalNumber = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etUserNationalID.getText().toString());
        RequestBody familyMembers = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etUserFamilyMembers.getText().toString());
        RequestBody gender;
        if (binding.userGenderMale.isChecked()) {
            gender = RequestBody.create(MediaType.parse("multipart/form-data"), "M");
        }
        else {
            gender = RequestBody.create(MediaType.parse("multipart/form-data"), "F");
        }
        userApiController.addUser(name, email, mobile, nationalNumber, familyMembers, gender, file, new ProcessCallback() {
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

    private void updateUser() {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "PUT");
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etUserFullname.getText().toString());
        RequestBody mobile = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etUserMobileNumber.getText().toString());
        RequestBody nationalNumber = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etUserNationalID.getText().toString());
        RequestBody familyMembers = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etUserFamilyMembers.getText().toString());
        RequestBody gender;
        if (binding.userGenderMale.isChecked()) {
            gender = RequestBody.create(MediaType.parse("multipart/form-data"), "M");
        }
        else {
            gender = RequestBody.create(MediaType.parse("multipart/form-data"), "F");
        }
        userApiController.updateUser(user_id, requestFile, name, mobile, nationalNumber, familyMembers, gender, file, new ProcessCallback() {
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
                    binding.imgUser.setImageBitmap(imageBitmap);
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