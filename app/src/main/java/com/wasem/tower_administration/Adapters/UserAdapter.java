package com.wasem.tower_administration.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wasem.tower_administration.Models.User;
import com.wasem.tower_administration.R;
import com.wasem.tower_administration.databinding.CustomUserItemBinding;
import com.wasem.tower_administration.interfaces.AdapterListener;

import java.util.ArrayList;
import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    List<User> users;
    Context context;
    AdapterListener listener;

    public UserAdapter(List<User> users, Context context, AdapterListener listener) {
        this.users = users;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomUserItemBinding binding = CustomUserItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new UserHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = users.get(position);
        Picasso.get().load(user.imageUrl).into(holder.binding.imgUserPhoto);
        holder.binding.cvUserName.setText(user.name);
        holder.binding.cvUserEmail.setText(user.email);
        holder.binding.cvUserMobile.setText(user.mobile);
        holder.binding.cvUserNationalNumber.setText(user.nationalNumber);
        holder.binding.cvUserFamilyMembers.setText(user.familyMembers);
        holder.binding.cvUserGender.setText(user.gender);
        holder.binding.imgUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUpdateClick(user.id);
            }
        });
        holder.binding.imgDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteClick(user.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}

class UserHolder extends RecyclerView.ViewHolder {
    CustomUserItemBinding binding;
    public UserHolder(@NonNull View itemView) {
        super(itemView);
        binding = CustomUserItemBinding.bind(itemView);
    }
}
