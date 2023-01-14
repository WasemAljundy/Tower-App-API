package com.wasem.tower_administration.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wasem.tower_administration.Models.Advertisement;
import com.wasem.tower_administration.databinding.CustomAdvertisementItemBinding;
import com.wasem.tower_administration.interfaces.AdvertisementAdapterListener;

import java.util.List;


public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementHolder> {

    List<Advertisement> advertisements;
    Context context;
    AdvertisementAdapterListener listener;

    public AdvertisementAdapter(List<Advertisement> advertisements, Context context, AdvertisementAdapterListener listener) {
        this.advertisements = advertisements;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdvertisementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomAdvertisementItemBinding binding = CustomAdvertisementItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new AdvertisementHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementHolder holder, int position) {
        Advertisement advertisement = advertisements.get(position);
        Picasso.get().load(advertisement.imageUrl).into(holder.binding.imgAdvertisement);
        holder.binding.cvAdvertisementTitle.setText(advertisement.title);
        holder.binding.cvAdvertisementInfo.setText(advertisement.info);
        holder.binding.imgUpdateAdvertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUpdateAds(advertisement.id);
            }
        });
        holder.binding.imgDeleteAdvertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteAds(advertisement.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return advertisements.size();
    }

}

class AdvertisementHolder extends RecyclerView.ViewHolder {
    CustomAdvertisementItemBinding binding;
    public AdvertisementHolder(@NonNull View itemView) {
        super(itemView);
        binding = CustomAdvertisementItemBinding.bind(itemView);
    }
}
