package com.stone.moviechannel.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stone.moviechannel.R;
import com.stone.moviechannel.adapter.MovieAdapter;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.databinding.FragmentComedyBinding;
import com.stone.moviechannel.listener.GetAllMovie;
import com.stone.moviechannel.listener.onClickMovie;
import com.stone.moviechannel.model.AppModel;
import com.stone.moviechannel.ui.activity.SingleMovieDetail;
import com.stone.moviechannel.ui.activity.VideoList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


public class ComedyFragment extends Fragment implements GetAllMovie, onClickMovie {
    private FragmentComedyBinding binding;
    private MovieAdapter adapter;
    private AppModel appModel;

    public ComedyFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comedy, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentComedyBinding.bind(view);
        appModel = AppModel.getINSTANCE(getActivity());

        adapter = new MovieAdapter(getContext(),this);

        appModel.getMovie(this,"comedy");

        binding.rcView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rcView.setHasFixedSize(true);
        binding.rcView.setAdapter(adapter);

        binding.seeAll.setOnClickListener(new View.OnClickListener() {
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