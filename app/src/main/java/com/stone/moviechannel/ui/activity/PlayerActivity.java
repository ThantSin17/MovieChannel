package com.stone.moviechannel.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.stone.moviechannel.databinding.ActivityPlayerBinding;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private static String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* setContentView(); */
        binding=ActivityPlayerBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        //Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        binding.vView.setVideoURI(Uri.parse(url));
        binding.vView.start();
    }
    public static Intent gotoPlayerActivity(Context context, String uri){
        url=uri;
        return new Intent(context,PlayerActivity.class);

    }
}