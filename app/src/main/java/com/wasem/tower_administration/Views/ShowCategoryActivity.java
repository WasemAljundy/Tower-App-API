package com.wasem.tower_administration.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.wasem.tower_administration.Adapters.CategoryAdapter;
import com.wasem.tower_administration.Adapters.CategoryShowAdapter;
import com.wasem.tower_administration.Api.controllers.ContentApiController;
import com.wasem.tower_administration.Models.Category;
import com.wasem.tower_administration.Models.CategoryShow;
import com.wasem.tower_administration.databinding.ActivityShowCategoryBinding;
import com.wasem.tower_administration.interfaces.CategoryAdapterListener;
import com.wasem.tower_administration.interfaces.ListCallback;

import java.util.ArrayList;
import java.util.List;

public class ShowCategoryActivity extends AppCompatActivity {
    ActivityShowCategoryBinding binding;
    CategoryShowAdapter adapter;
    ContentApiController contentApiController = new ContentApiController();
    List<CategoryShow> categoryShows = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    private void initializeView() {
        showCategories();
    }

    private void initializeRecyclerAdapter() {
        adapter = new CategoryShowAdapter(categoryShows, getApplicationContext());
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);
    }

    private void showCategories() {
        int categoryId = getIntent().getIntExtra("category_id",0);
        contentApiController.showCategories(categoryId, new ListCallback<CategoryShow>() {
            @Override
            public void onSuccess(List<CategoryShow> list) {
                categoryShows.addAll(list);
                initializeRecyclerAdapter();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

}