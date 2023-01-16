package com.wasem.tower_administration.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.tower_administration.Adapters.EmployeeActorSpinnerAdapter;
import com.wasem.tower_administration.Adapters.UsersActorSpinnerAdapter;
import com.wasem.tower_administration.Api.controllers.ApiController;
import com.wasem.tower_administration.Api.controllers.ContentApiController;
import com.wasem.tower_administration.Api.controllers.OperationApiController;
import com.wasem.tower_administration.Models.BaseResponse;
import com.wasem.tower_administration.Models.Employee;
import com.wasem.tower_administration.Models.Operation;
import com.wasem.tower_administration.Models.User;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.ActivityAddOperationBinding;
import com.wasem.tower_administration.interfaces.ListCallback;
import com.wasem.tower_administration.interfaces.ProcessCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOperationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    ActivityAddOperationBinding binding;
    ContentApiController contentApiController = new ContentApiController();
    OperationApiController operationApiController = new OperationApiController();
    EmployeeActorSpinnerAdapter employeeActorSpinnerAdapter;
    UsersActorSpinnerAdapter usersActorSpinnerAdapter;
    int operation_id;
    List<Employee> employeeList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    String actorType, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOperationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupView();
        initializeView();
    }

    private void setupView(){
        Intent intent = getIntent();
        operation_id = intent.getIntExtra("operation_id",0);
        String title =  intent.getStringExtra("operation_update_title");
        if ( operation_id != 0 && title != null) {
            binding.tvAddOperationWelcome.setText(title);
            binding.btnAddOperation.setVisibility(View.GONE);
            binding.btnUpdateOperation.setVisibility(View.VISIBLE);
        }
    }

    private void initializeView() {
        getEmployeeActors();
        getUsersActors();
        categorySpinnerInitialize();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        binding.tvOperationDate.setOnClickListener(this);
        binding.btnAddOperation.setOnClickListener(this);
        binding.btnUpdateOperation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_operation_date) {
            getSelectedDate();
        }
        else if (view.getId() == R.id.btn_add_operation) {
            addOperation();
        }
        else if (view.getId() == R.id.btn_update_operation) {
            updateOperation(operation_id);
        }
    }

    private void getEmployeeActors() {
        contentApiController.getEmployees(new ListCallback<Employee>() {
            @Override
            public void onSuccess(List<Employee> list) {
                employeeList.addAll(list);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AddOperationActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUsersActors() {
        contentApiController.getUsers(new ListCallback<User>() {
            @Override
            public void onSuccess(List<User> list) {
                userList.addAll(list);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(AddOperationActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void categorySpinnerInitialize(){
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategory.setAdapter(arrayAdapter);
        binding.spinnerCategory.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();
        checkSpinnerCategory(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private void checkSpinnerCategory(int categoryId){
        if (categoryId == 0) {
            binding.spinnerActor.setEnabled(false);
        }
        else if (categoryId == 2) {
            binding.spinnerActor.setEnabled(true);
            employeeActorSpinnerInitialize();
            actorType = "Employee";
        }
        else {
            binding.spinnerActor.setEnabled(true);
            usersActorSpinnerInitialize();
            actorType = "Resident";
        }
    }

    private void employeeActorSpinnerInitialize(){
        employeeActorSpinnerAdapter = new EmployeeActorSpinnerAdapter(employeeList);
        binding.spinnerActor.setAdapter(employeeActorSpinnerAdapter);
        binding.spinnerActor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AddOperationActivity.this, String.valueOf(adapterView.getSelectedItemId()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void usersActorSpinnerInitialize(){
        usersActorSpinnerAdapter = new UsersActorSpinnerAdapter(userList);
        binding.spinnerActor.setAdapter(usersActorSpinnerAdapter);
        binding.spinnerActor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AddOperationActivity.this, String.valueOf(adapterView.getSelectedItemId()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void getSelectedDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        now.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");
                        date = dateFormat.format(now.getTime());
                        binding.tvOperationDate.setText(date);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        dpd.show(getSupportFragmentManager(), "DatePickerDialog");
    }

    private void addOperation(){
        operationApiController.addOperation(binding.spinnerCategory.getSelectedItemId(), Integer.parseInt(binding.tvOperationAmount.getText().toString()), binding.etOperationDetails.getText().toString(), binding.spinnerActor.getSelectedItemId(), actorType, date, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                FancyToast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT, FancyToast.SUCCESS,false).show();
            }

            @Override
            public void onFailure(String message) {
                FancyToast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            }
        });
    }

    private void updateOperation(int id){
        operationApiController.updateOperation(id, binding.spinnerCategory.getSelectedItemId(), Integer.parseInt(binding.tvOperationAmount.getText().toString()), binding.etOperationDetails.getText().toString(), binding.spinnerActor.getSelectedItemId(), actorType, date, new ProcessCallback() {
            @Override
            public void onSuccess(String message) {
                FancyToast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
            }

            @Override
            public void onFailure(String message) {
                FancyToast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            }
        });
    }

}