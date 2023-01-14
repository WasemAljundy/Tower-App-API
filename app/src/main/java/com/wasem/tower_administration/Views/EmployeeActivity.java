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
import com.wasem.tower_administration.Adapters.EmployeeAdapter;
import com.wasem.tower_administration.Adapters.UserAdapter;
import com.wasem.tower_administration.Api.controllers.ApiController;
import com.wasem.tower_administration.Api.controllers.ContentApiController;
import com.wasem.tower_administration.Models.BaseResponse;
import com.wasem.tower_administration.Models.Employee;
import com.wasem.tower_administration.Models.User;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityAddEmployeeBinding;
import com.wasem.tower_administration.databinding.ActivityEmployeeBinding;
import com.wasem.tower_administration.interfaces.AdapterListener;
import com.wasem.tower_administration.interfaces.ListCallback;
import com.wasem.tower_administration.interfaces.ProcessCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeActivity extends AppCompatActivity {
    ActivityEmployeeBinding binding;
    ContentApiController contentApiController = new ContentApiController();
    List<Employee> employees = new ArrayList<>();
    EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeBinding.inflate(getLayoutInflater());
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
        getMenuInflater().inflate(R.menu.employee_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add_employees) {
            Intent intent = new Intent(getApplicationContext(),AddEmployeeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void initializeView() {
        getEmployee();
    }

    private void initializeRecyclerAdapter() {
        adapter = new EmployeeAdapter(employees, getApplicationContext(), new AdapterListener() {
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

    private void getEmployee() {
        contentApiController.getEmployees(new ListCallback<Employee>() {
            @Override
            public void onSuccess(List<Employee> list) {
                employees.addAll(list);
                initializeRecyclerAdapter();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void update(int id) {
        Intent intent = new Intent(getApplicationContext(),AddEmployeeActivity.class);
        intent.putExtra("emp_id",id);
        intent.putExtra("emp_update_title","Update Employee");
        startActivity(intent);
    }

    public void delete(int id) {
        Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequests().deleteEmployee(id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    FancyToast.makeText(getApplicationContext(),"Employee Deleted Successfully!",Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                    recreate();
                }
            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(),"Can't delete this employee!",Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            }
        });
    }
}