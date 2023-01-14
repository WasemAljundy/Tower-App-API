package com.wasem.tower_administration.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wasem.tower_administration.Models.Category;
import com.wasem.tower_administration.Models.CategoryShow;
import com.wasem.tower_administration.databinding.CustomCategoryItemBinding;
import com.wasem.tower_administration.databinding.CustomCategoryShowItemBinding;

import java.util.ArrayList;
import java.util.List;


public class CategoryShowAdapter extends RecyclerView.Adapter<CategoryShowHolder> {

    List<CategoryShow> categoryShows;
    Context context;

    public CategoryShowAdapter(List<CategoryShow> categoryShows, Context context) {
        this.categoryShows = categoryShows;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomCategoryShowItemBinding binding = CustomCategoryShowItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CategoryShowHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryShowHolder holder, int position) {
        CategoryShow categoryShow = categoryShows.get(position);
        holder.binding.cvCategoryShowName.setText(categoryShow.categoryName);
        holder.binding.cvCategoryShowDetails.setText(categoryShow.details);
        holder.binding.cvCategoryShowDate.setText(String.valueOf(categoryShow.date));
        holder.binding.cvCategoryShowAmount.setText(String.valueOf(categoryShow.amount));
    }

    @Override
    public int getItemCount() {
        return categoryShows.size();
    }
}

class CategoryShowHolder extends RecyclerView.ViewHolder {
    CustomCategoryShowItemBinding binding;
    public CategoryShowHolder(@NonNull View itemView) {
        super(itemView);
        binding = CustomCategoryShowItemBinding.bind(itemView);
    }
}
