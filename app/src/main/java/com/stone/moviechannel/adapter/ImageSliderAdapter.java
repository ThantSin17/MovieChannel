package com.stone.moviechannel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.stone.moviechannel.data.ImageSlide;
import com.stone.moviechannel.databinding.ImageSlideItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>{
    private Context context;
    private List<ImageSlide> imageSlideList;

    public ImageSliderAdapter(Context context) {
        this.context = context;
        imageSlideList=new ArrayList<>();
    }

    public void setImageSlideList(List<ImageSlide> imageSlideList) {
        this.imageSlideList = imageSlideList;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageSliderViewHolder(ImageSlideItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        ImageSlide imageSlide=imageSlideList.get(position);
        holder.bind(imageSlide);
    }

    @Override
    public int getItemCount() {
        return imageSlideList.size();
    }

    protected class ImageSliderViewHolder extends RecyclerView.ViewHolder {
        ImageSlideItemLayoutBinding binding;
        public ImageSliderViewHolder(@NonNull ImageSlideItemLayoutBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
        public void bind(ImageSlide imageSlide){
            Glide.with(context).load(imageSlide.getImageUrl()).into(binding.slideImage);
        }
    }
}
