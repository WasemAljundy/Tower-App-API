package com.wasem.tower_administration.Views;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.tower_administration.Api.controllers.AdvertisementApiController;
import com.wasem.tower_administration.Models.Advertisement;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityAddAdvertisementBinding;
import com.wasem.tower_administration.interfaces.ProcessCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.PUT;

public class AddAdvertisementActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAddAdvertisementBinding binding;
    AdvertisementApiController advertisementApiController = new AdvertisementApiController();
    int adsId;
    private ActivityResultLauncher<String> permissionResultLauncher;
    private ActivityResultLauncher<String> getImageResultLauncher;
    private Bitmap imageBitmap;
    private MultipartBody.Part file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAdvertisementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupUpdateView();
        initializeView();
    }

    private void setupUpdateView(){
        Intent intent = getIntent();
        adsId = intent.getIntExtra("ads_id",0);
        String title =  intent.getStringExtra("advertisement_update_title");
        if (adsId != 0 && title != null) {
            binding.tvAddAdsWelcome.setText(title);
            binding.btnAddAdvertisement.setVisibility(View.GONE);
            binding.btnUpdateAdvertisement.setVisibility(View.VISIBLE);
        }
    }

    private void initializeView() {
        setupResultsLauncher();
        onClickListeners();
    }

    private void onClickListeners() {
        binding.btnAddAdvertisement.setOnClickListener(this);
        binding.btnUpdateAdvertisement.setOnClickListener(this);
        binding.imgAdvertisement.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_advertisement) {
            performImageUpload();
            addAdvertisement();
        }
        else if (view.getId() == R.id.btn_update_advertisement) {
            performImageUpload();
            updateAdvertisement();
        }
        else if (view.getId() == R.id.img_advertisement) {
            pickImage();
        }
    }

    private void addAdvertisement() {
        RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etAdvertisementTitle.getText().toString());
        RequestBody info = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etAdvertisementInfo.getText().toString());
        advertisementApiController.addAdvertisement(title, info, file, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(AddAdvertisementActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AddAdvertisementActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAdvertisement() {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "PUT");
        RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etAdvertisementTitle.getText().toString());
        RequestBody info = RequestBody.create(MediaType.parse("multipart/form-data"), binding.etAdvertisementInfo.getText().toString());
        advertisementApiController.updateAdvertisement(adsId, requestFile, title, info, file, new ProcessCallback() {
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
                    getImageResultLauncher.launch("image/*");
                }
            }
        });

        getImageResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    try {
                        imageBitmap = BitmapFactory.decodeStream(getApplicationContext().getContentResolver().openInputStream(result));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    binding.imgAdvertisement.setImageBitmap(imageBitmap);
                    binding.tvAddAdsPhoto.setVisibility(View.GONE);
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