package com.wasem.tower_administration.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wasem.tower_administration.Models.Employee;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.CustomEmployeeItemBinding;
import com.wasem.tower_administration.interfaces.AdapterListener;

import java.util.ArrayList;
import java.util.List;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeHolder> {

    List<Employee> employees;
    Context context;
    AdapterListener listener;

    public EmployeeAdapter(List<Employee> employees, Context context, AdapterListener listener) {
        this.employees = employees;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomEmployeeItemBinding binding = CustomEmployeeItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new EmployeeHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
        Employee employee = employees.get(position);
        Picasso.get().load(employee.imageUrl).into(holder.binding.imgEmployeePhoto);
        holder.binding.cvEmployeeName.setText(employee.name);
        holder.binding.cvEmployeeNationalNumber.setText(employee.nationalNumber);
        holder.binding.cvEmployeeMobile.setText(employee.mobile);
        holder.binding.imgUpdateEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUpdateClick(employee.id);
            }
        });
        holder.binding.imgDeleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteClick(employee.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

}

class EmployeeHolder extends RecyclerView.ViewHolder {
    CustomEmployeeItemBinding binding;
    public EmployeeHolder(@NonNull View itemView) {
        super(itemView);
        binding = CustomEmployeeItemBinding.bind(itemView);
    }
}
