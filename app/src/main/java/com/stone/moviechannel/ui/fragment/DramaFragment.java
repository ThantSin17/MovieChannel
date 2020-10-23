package com.stone.moviechannel.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stone.moviechannel.R;
import com.stone.moviechannel.adapter.MovieAdapter;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.databinding.FragmentDramaBinding;
import com.stone.moviechannel.listener.GetAllMovie;
import com.stone.moviechannel.listener.onClickMovie;
import com.stone.moviechannel.model.AppModel;
import com.stone.moviechannel.ui.activity.SingleMovieDetail;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


public class DramaFragment extends Fragment implements GetAllMovie, onClickMovie {

    private FragmentDramaBinding binding;
    //private List<Movie> movieList;
    MovieAdapter adapter;
    private AppModel appModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drama, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=FragmentDramaBinding.bind(view);
        appModel=AppModel.getINSTANCE();



        adapter = new MovieAdapter(this.getContext(),this);
        appModel.getMovie(this.getContext(),this);

        binding.rcDrama.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.rcDrama.setHasFixedSize(true);
        binding.rcDrama.setAdapter(adapter);


    }


    @Override
    public void getAllMovie(List<Movie> movies) {

        adapter.setMovieList(movies);
        Toast.makeText(getContext(),movies.size()+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail(String message) {

    }

    @Override
    public void clickMovie(Movie movie) {
        startActivity(SingleMovieDetail.gotoSingleMovieDetail(this.getContext(),movie));
    }
}
