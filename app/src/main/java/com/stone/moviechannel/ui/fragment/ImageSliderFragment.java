package com.stone.moviechannel.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.stone.moviechannel.R;
import com.stone.moviechannel.adapter.ImageSliderAdapter;
import com.stone.moviechannel.data.ImageSlide;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.databinding.FragmentImageSliderBinding;
import com.stone.moviechannel.listener.GetAllMovie;
import com.stone.moviechannel.listener.onClickMovie;
import com.stone.moviechannel.model.AppModel;
import com.stone.moviechannel.ui.activity.SingleMovieDetail;

import java.util.List;


public class ImageSliderFragment extends Fragment implements onClickMovie, GetAllMovie {

   private AppModel appModel;
   private FragmentImageSliderBinding binding;
    private Handler handler=new Handler();
    private int currentItem=0;
    private List<Movie> imageSlides;
   private ImageSliderAdapter adapter;
    public ImageSliderFragment() {
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
        return inflater.inflate(R.layout.fragment_image_slider, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=FragmentImageSliderBinding.bind(view);
        appModel=AppModel.getINSTANCE(getContext());
        adapter=new ImageSliderAdapter(getContext(),this);
        appModel.getMovie(this,"menu");
        binding.imageSlider.setAdapter(adapter);
        new TabLayoutMediator(binding.indicator, binding.imageSlider, (tab, position) -> {

        }).attach();
        binding.imageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(slider);
                handler.postDelayed(slider,5000);
            }
        });
    }

    @Override
    public void clickMovie(Movie movie) {
        Toast.makeText(getContext(), movie.title, Toast.LENGTH_SHORT).show();
        startActivity(SingleMovieDetail.gotoSingleMovieDetail(getContext(),movie));
    }

    private Runnable slider=new Runnable() {
        @Override
        public void run() {
            if (currentItem==imageSlides.size()-1){
                currentItem=0;
                binding.imageSlider.setCurrentItem(currentItem);
            }else {
                binding.imageSlider.setCurrentItem(++currentItem);
            }

        }
    };
    @Override
    public void getAllMovie(List<Movie> movies) {
        imageSlides=movies;
        adapter.setImageSlideList(movies);
    }

    @Override
    public void fail(String message) {

    }
}