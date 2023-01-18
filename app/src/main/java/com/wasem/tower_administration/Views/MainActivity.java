package com.wasem.tower_administration.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.tower_administration.Adapters.CategoryAdapter;
import com.wasem.tower_administration.Adapters.CategoryShowAdapter;
import com.wasem.tower_administration.Api.controllers.AuthApiController;
import com.wasem.tower_administration.Api.controllers.ContentApiController;
import com.wasem.tower_administration.Models.Category;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityMainBinding;
import com.wasem.tower_administration.interfaces.CategoryAdapterListener;
import com.wasem.tower_administration.interfaces.ListCallback;
import com.wasem.tower_administration.interfaces.ProcessCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    AuthApiController authApiController = new AuthApiController();
    ContentApiController contentApiController = new ContentApiController();
    List<Category> categories = new ArrayList<>();
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            logout();
        }
        else if (item.getItemId() == R.id.menu_users_activity) {
            Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menu_employees_activity) {
            Intent intent = new Intent(getApplicationContext(),EmployeeActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menu_operations_activity) {
            Intent intent = new Intent(getApplicationContext(),OperationActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menu_advertisement_activity) {
            Intent intent = new Intent(getApplicationContext(),AdvertisementActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.menu_change_password_activity) {
            Intent intent = new Intent(getApplicationContext(),ChangePasswordActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeView() {
        getCategories();
    }

    private void initializeRecyclerAdapter() {
        adapter = new CategoryAdapter(categories, getApplicationContext(), new CategoryAdapterListener() {
            @Override
            public void onCategoryClicked(int categoryId) {
                Intent intent = new Intent(getApplicationContext(),ShowCategoryActivity.class);
                intent.putExtra("category_id",categoryId);
                startActivity(intent);
            }
        });
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);
    }

    private void getCategories() {
        contentApiController.getCategories(new ListCallback<Category>() {
            @Override
            public void onSuccess(List<Category> list) {
                categories.addAll(list);
                initializeRecyclerAdapter();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void logout() {
        authApiController.logout(new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                FancyToast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(String message) {
                FancyToast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT,FancyToast.WARNING,false).show();
            }
        });
    }
}