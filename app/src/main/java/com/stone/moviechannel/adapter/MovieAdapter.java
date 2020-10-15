package com.stone.moviechannel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stone.moviechannel.R;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.databinding.MovieItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context) {
        this.context = context;
        movieList=new ArrayList<>();
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie=movieList.get(position);

        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        MovieItemLayoutBinding binding;
        public MovieViewHolder(@NonNull MovieItemLayoutBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
        public void bind(Movie movie){
            binding.itemTitle.setText(movie.getTitle());
            Glide.with(context).load(movie.getImageLink()).placeholder(R.drawable.loading_pinner).into(binding.itemImage);
//            Toast.makeText(context, "finish", Toast.LENGTH_SHORT).show();
        }
    }

}
