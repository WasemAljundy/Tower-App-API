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
import com.wasem.tower_administration.Adapters.CategoryShowAdapter;
import com.wasem.tower_administration.Adapters.OperationAdapter;
import com.wasem.tower_administration.Api.controllers.ContentApiController;
import com.wasem.tower_administration.Api.controllers.OperationApiController;
import com.wasem.tower_administration.Models.CategoryShow;
import com.wasem.tower_administration.Models.Employee;
import com.wasem.tower_administration.Models.Operation;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityOperationBinding;
import com.wasem.tower_administration.interfaces.AdapterListener;
import com.wasem.tower_administration.interfaces.ListCallback;
import com.wasem.tower_administration.interfaces.ProcessCallback;

import java.util.ArrayList;
import java.util.List;

public class OperationActivity extends AppCompatActivity {
    ActivityOperationBinding binding;
    OperationAdapter adapter;
    ContentApiController contentApiController = new ContentApiController();
    OperationApiController operationApiController = new OperationApiController();
    List<Operation> operations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOperationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        operations.clear();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.operation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add_operation) {
            Intent intent = new Intent(getApplicationContext(),AddOperationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeView() {
        getOperations();
    }

    private void initializeRecyclerAdapter() {
        adapter = new OperationAdapter(operations, getApplicationContext(), new AdapterListener() {
            @Override
            public void onUpdateClick(int id) {
                updateOperation(id);
            }

            @Override
            public void onDeleteClick(int id) {
                deleteOperation(id);
            }
        });
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);
    }

    private void getOperations() {
        contentApiController.getOperations(new ListCallback<Operation>() {
            @Override
            public void onSuccess(List<Operation> list) {
                operations.addAll(list);
                initializeRecyclerAdapter();
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void updateOperation(int id){
        Intent intent = new Intent(getApplicationContext(),AddOperationActivity.class);
        intent.putExtra("operation_id",id);
        intent.putExtra("operation_update_title","Update Operation");
        startActivity(intent);
    }

    private void deleteOperation(int id){
        operationApiController.deleteOperation(id, new ProcessCallback() {
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