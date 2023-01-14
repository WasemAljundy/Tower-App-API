package com.wasem.tower_administration.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wasem.tower_administration.Models.Category;
import com.wasem.tower_administration.databinding.CustomCategoryItemBinding;
import com.wasem.tower_administration.interfaces.AdapterListener;
import com.wasem.tower_administration.interfaces.CategoryAdapterListener;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

    List<Category> categories;
    Context context;
    CategoryAdapterListener listener;

    public CategoryAdapter(List<Category> categories, Context context, CategoryAdapterListener listener) {
        this.categories = categories;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomCategoryItemBinding binding = CustomCategoryItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CategoryHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.cvCategoryName.setText(category.name);
        holder.binding.cvCategoryActionsCount.setText(category.actionsCount);
        holder.binding.cvCategoryTotal.setText(String.valueOf(category.total));
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCategoryClicked(category.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}

class CategoryHolder extends RecyclerView.ViewHolder {
    CustomCategoryItemBinding binding;
    public CategoryHolder(@NonNull View itemView) {
        super(itemView);
        binding = CustomCategoryItemBinding.bind(itemView);
    }
}
