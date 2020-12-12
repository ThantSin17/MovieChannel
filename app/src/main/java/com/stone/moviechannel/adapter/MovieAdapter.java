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
import com.stone.moviechannel.listener.onClickMovie;
import com.stone.moviechannel.model.AppModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private onClickMovie listener;
    private List<Movie> movieList;
    private List<Movie> tempList;
    private AppModel appModel;

    public MovieAdapter(Context context, onClickMovie listener) {
        this.context = context;
        this.listener = listener;
        appModel=AppModel.getINSTANCE(context);
        movieList = new ArrayList<>();
        tempList=new ArrayList<>();
    }

    public MovieAdapter(Context context) {
        this.context = context;
        movieList = new ArrayList<>();
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        tempList=movieList;
        notifyDataSetChanged();
    }
    public void filter(String s){
        if (s.isEmpty() || s.equals(" ")){
            movieList=tempList;
        }else {
            List<Movie> temp=new ArrayList<>();
            for (Movie movie: tempList){
                if (movie.title.toLowerCase().contains(s.toLowerCase())){
                    temp.add(movie);
                }
            }
            movieList=temp;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        holder.binding.itemImage.setOnClickListener(view -> listener.clickMovie(appModel.getMovieById(movie.id)));
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

    public List<Movie> getData() {
        return movieList;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        MovieItemLayoutBinding binding;

        public MovieViewHolder(@NonNull MovieItemLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(Movie movie) {

            binding.itemTitle.setText(movie.title);
            Glide.with(context).load(movie.imageLink).placeholder(R.drawable.progress_animation).into(binding.itemImage);
//          Toast.makeText(context, "finish", Toast.LENGTH_SHORT).show();

        }
    }

}
