package com.wasem.tower_administration.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wasem.tower_administration.Models.Employee;
import com.wasem.tower_administration.R;

import java.util.List;

public class EmployeeActorSpinnerAdapter extends BaseAdapter {

    private List<Employee> employees;

    public EmployeeActorSpinnerAdapter(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int i) {
        return employees.get(i);
    }

    @Override
    public long getItemId(int i) {
        return employees.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_spinner_item,viewGroup,false);
        }
        TextView tv = v.findViewById(R.id.tv_spinner_name);
        Employee e = (Employee) getItem(i);
        tv.setText(e.name);
        return v;
    }
}
