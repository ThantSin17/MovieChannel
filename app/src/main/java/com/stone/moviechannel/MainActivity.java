package com.stone.moviechannel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.stone.moviechannel.databinding.ActivityMainBinding;
import com.stone.moviechannel.ui.fragment.ActionFragment;
import com.stone.moviechannel.ui.fragment.DramaFragment;
import com.stone.moviechannel.ui.fragment.SeriesFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
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