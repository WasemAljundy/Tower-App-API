package com.wasem.tower_administration.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.tower_administration.Adapters.AdvertisementAdapter;
import com.wasem.tower_administration.Api.controllers.AdvertisementApiController;
import com.wasem.tower_administration.Api.controllers.ContentApiController;
import com.wasem.tower_administration.Models.Advertisement;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityAdvertisementBinding;
import com.wasem.tower_administration.interfaces.AdvertisementAdapterListener;
import com.wasem.tower_administration.interfaces.ListCallback;
import com.wasem.tower_administration.interfaces.ProcessCallback;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementActivity extends AppCompatActivity {
    ActivityAdvertisementBinding binding;
    AdvertisementAdapter adapter;
    List<Advertisement> advertisementList = new ArrayList<>();
    ContentApiController contentApiController = new ContentApiController();
    AdvertisementApiController advertisementApiController = new AdvertisementApiController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdvertisementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.advertisement_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add_advertisement) {
            Intent intent = new Intent(getApplicationContext(),AddAdvertisementActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeView() {
        getAdvertisement();
    }

    private void initializeAdvertisementAdapter() {
        adapter = new AdvertisementAdapter(advertisementList, getApplicationContext(), new AdvertisementAdapterListener() {
            @Override
            public void onUpdateAds(int adsId) {
                updateOperation(adsId);
            }

            @Override
            public void onDeleteAds(int adsId) {
                deleteOperation(adsId);
            }
        });
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);
    }

    private void getAdvertisement() {
        contentApiController.getAdvertisements(new ListCallback<Advertisement>() {
            @Override
            public void onSuccess(List<Advertisement> list) {
                advertisementList.addAll(list);
                initializeAdvertisementAdapter();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void updateOperation(int id){
        Intent intent = new Intent(getApplicationContext(),AddAdvertisementActivity.class);
        intent.putExtra("ads_id",id);
        intent.putExtra("advertisement_update_title","Update Advertisement");
        startActivity(intent);
    }

    private void deleteOperation(int id){
        advertisementApiController.deleteAdvertisement(id, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                FancyToast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                recreate();
            }

            @Override
            public void onFailure(String message) {
                FancyToast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            }
        });
    }
}