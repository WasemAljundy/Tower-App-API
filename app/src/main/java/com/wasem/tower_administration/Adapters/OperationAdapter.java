package com.wasem.tower_administration.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.wasem.tower_administration.Models.Operation;
import com.wasem.tower_administration.databinding.CustomOperationItemBinding;
import com.wasem.tower_administration.interfaces.AdapterListener;

import java.util.List;


public class OperationAdapter extends RecyclerView.Adapter<OperationHolder> {

    List<Operation> operations;
    Context context;
    AdapterListener listener;

    public OperationAdapter(List<Operation> operations, Context context, AdapterListener listener) {
        this.operations = operations;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OperationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomOperationItemBinding binding = CustomOperationItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new OperationHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull OperationHolder holder, int position) {
        Operation operation = operations.get(position);
        holder.binding.cvCategoryOperationName.setText(operation.categoryName);
        holder.binding.cvCategoryOperationDetails.setText(operation.details);
        holder.binding.cvCategoryOperationDate.setText(operation.date);
        holder.binding.cvCategoryOperationShowAmount.setText(String.valueOf(operation.amount));

        holder.binding.imgUpdateOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUpdateClick(operation.id);
            }
        });
        holder.binding.imgDeleteOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteClick(operation.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }

}

class OperationHolder extends RecyclerView.ViewHolder {
    CustomOperationItemBinding binding;
    public OperationHolder(@NonNull View itemView) {
        super(itemView);
        binding = CustomOperationItemBinding.bind(itemView);
    }
}
