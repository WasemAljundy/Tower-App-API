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
import com.wasem.tower_administration.Adapters.UserAdapter;
import com.wasem.tower_administration.Api.controllers.ApiController;
import com.wasem.tower_administration.Api.controllers.ContentApiController;
import com.wasem.tower_administration.Models.BaseResponse;
import com.wasem.tower_administration.Models.User;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityUserBinding;
import com.wasem.tower_administration.interfaces.AdapterListener;
import com.wasem.tower_administration.interfaces.ListCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    ActivityUserBinding binding;
    ContentApiController contentApiController = new ContentApiController();
    List<User> users = new ArrayList<>();
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
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
        getMenuInflater().inflate(R.menu.users_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add_users) {
            Intent intent = new Intent(getApplicationContext(),AddUserActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void initializeView() {
        getUsers();
    }

    private void initializeRecyclerAdapter() {
        adapter = new UserAdapter(users, getApplicationContext(), new AdapterListener() {
            @Override
            public void onUpdateClick(int id) {
                update(id);
            }

            @Override
            public void onDeleteClick(int id) {
                delete(id);
            }
        });
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);
    }

    private void getUsers() {
        contentApiController.getUsers(new ListCallback<User>() {
            @Override
            public void onSuccess(List<User> list) {
                users.addAll(list);
                initializeRecyclerAdapter();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void update(int id) {
        Intent intent = new Intent(getApplicationContext(),AddUserActivity.class);
        intent.putExtra("user_id",id);
        intent.putExtra("user_update_title","Update User");
        startActivity(intent);
    }

    public void delete(int id) {
        Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequests().deleteUser(id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    FancyToast.makeText(getApplicationContext(),"User Deleted Successfully!", Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                    recreate();
                }
            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(),"Can't delete this User!",Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            }
        });
    }
}