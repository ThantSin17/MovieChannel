package com.stone.moviechannel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.stone.moviechannel.adapter.ImageSliderAdapter;
import com.stone.moviechannel.data.ImageSlide;
import com.stone.moviechannel.databinding.ActivityMainBinding;
import com.stone.moviechannel.ui.fragment.ActionFragment;
import com.stone.moviechannel.ui.fragment.DramaFragment;
import com.stone.moviechannel.ui.fragment.SeriesFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        ImageSliderAdapter adapter=new ImageSliderAdapter(this);

        binding.imageSlider.setAdapter(adapter);
        List<ImageSlide> imageSlides=new ArrayList<>();

        ImageSlide slide=new ImageSlide();
        slide.setImageUrl("https://1.bp.blogspot.com/-hu8UkD96vUo/XfUqEBvgB6I/AAAAAAAABDg/evxNz84MTQA1NbairvLaKVqYpc-8wnMKACLcBGAsYHQ/s1600/spiderman_homecoming.jpg");
        imageSlides.add(slide);
        ImageSlide slide2=new ImageSlide();
        slide2.setImageUrl("https://1.bp.blogspot.com/-hu8UkD96vUo/XfUqEBvgB6I/AAAAAAAABDg/evxNz84MTQA1NbairvLaKVqYpc-8wnMKACLcBGAsYHQ/s1600/spiderman_homecoming.jpg");
        imageSlides.add(slide2);
        adapter.setImageSlideList(imageSlides);

        new TabLayoutMediator(binding.indicator, binding.imageSlider, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

            }
        }).attach();


        ActionFragment fragAction=new ActionFragment();
        DramaFragment fragDrama=new DramaFragment();
        SeriesFragment fragSeries=new SeriesFragment();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.action_layout,fragAction);
        transaction.add(R.id.drama_layout,fragDrama);
        transaction.add(R.id.series_layout,fragSeries);
        transaction.commit();
    }
}