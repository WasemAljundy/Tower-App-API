package com.wasem.tower_administration.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wasem.tower_administration.Models.Employee;
import com.wasem.tower_administration.Models.User;
import com.wasem.tower_administration.R;

import java.util.List;

public class UsersActorSpinnerAdapter extends BaseAdapter {

    private List<User> users;

    public UsersActorSpinnerAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return users.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_spinner_item,viewGroup,false);
        }
        TextView tv = v.findViewById(R.id.tv_spinner_name);
        User u = (User) getItem(i);
        tv.setText(u.name);
        return v;
    }
}
