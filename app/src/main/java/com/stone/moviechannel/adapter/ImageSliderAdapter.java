package com.stone.moviechannel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stone.moviechannel.data.ImageSlide;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.databinding.ImageSlideItemLayoutBinding;
import com.stone.moviechannel.listener.onClickMovie;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>{
    private Context context;
    private List<Movie> imageSlideList;
    private onClickMovie listener;

    public ImageSliderAdapter(Context context,onClickMovie listener) {
        this.context = context;
        this.listener=listener;
        imageSlideList=new ArrayList<>();
    }

    public void setImageSlideList(List<Movie> imageSlideList) {
        this.imageSlideList = imageSlideList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageSliderViewHolder(ImageSlideItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        Movie movie=imageSlideList.get(position);
        holder.binding.slideImage.setOnClickListener(view -> {

            listener.clickMovie(movie);
        });
        holder.bind(movie);
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
        public void bind(Movie movie){
            Glide.with(context).load(movie.imageLink).into(binding.slideImage);
        }
    }
}
