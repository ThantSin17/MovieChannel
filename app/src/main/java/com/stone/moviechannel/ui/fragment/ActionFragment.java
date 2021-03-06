package com.stone.moviechannel.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stone.moviechannel.R;
import com.stone.moviechannel.ui.activity.VideoList;
import com.stone.moviechannel.adapter.MovieAdapter;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.databinding.FragmentActionBinding;
import com.stone.moviechannel.listener.GetAllMovie;
import com.stone.moviechannel.listener.onClickMovie;
import com.stone.moviechannel.model.AppModel;
import com.stone.moviechannel.ui.activity.SingleMovieDetail;

import java.util.List;

public class ActionFragment extends Fragment implements GetAllMovie, onClickMovie {

    private FragmentActionBinding binding;
    MovieAdapter adapter;
    private AppModel appModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentActionBinding.bind(view);
        appModel = AppModel.getINSTANCE(getActivity());

        adapter = new MovieAdapter(getContext(),this);

        appModel.getMovie(this,"action");

        binding.rcAction.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rcAction.setHasFixedSize(true);
        binding.rcAction.setAdapter(adapter);

        binding.seeAllAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(VideoList.gotoVideoList(getContext(),adapter.getData()));
            }
        });


    }

    @Override
    public void getAllMovie(List<Movie> movies) {

        adapter.setMovieList(movies);
    }

    @Override
    public void fail(String message) {

    }

    @Override
    public void clickMovie(Movie movie) {
        startActivity(SingleMovieDetail.gotoSingleMovieDetail(this.getContext(),movie));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}