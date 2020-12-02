package com.stone.moviechannel.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stone.moviechannel.R;
import com.stone.moviechannel.adapter.MovieAdapter;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.databinding.FragmentDramaBinding;
import com.stone.moviechannel.databinding.FragmentSeriesBinding;

import java.util.ArrayList;
import java.util.List;


public class SeriesFragment extends Fragment {

    private FragmentSeriesBinding binding;
    private List<Movie> movieList;
    private MovieAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=FragmentSeriesBinding.bind(view);

        movieList=new ArrayList<>();

        adapter = new MovieAdapter(getContext());
        adapter.setMovieList(movieList);
        binding.rcSeries.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.rcSeries.setHasFixedSize(true);
        binding.rcSeries.setAdapter(adapter);


    }


}
