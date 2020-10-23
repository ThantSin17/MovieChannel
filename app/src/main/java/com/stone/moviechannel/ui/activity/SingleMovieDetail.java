package com.stone.moviechannel.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.htetznaing.lowcostvideo.LowCostVideo;
import com.htetznaing.lowcostvideo.Model.XModel;
import com.stone.moviechannel.R;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.data.VideoDetail;
import com.stone.moviechannel.databinding.ChooseQualityLayoutBinding;
import com.stone.moviechannel.databinding.SingleMovieDetailBinding;
import com.stone.moviechannel.dialog.ChooseQuality;

import java.util.ArrayList;

public class SingleMovieDetail extends AppCompatActivity {

    private SingleMovieDetailBinding dataBinding;
    private ChooseQualityLayoutBinding binding;
    private AlertDialog dialog;
    private static Movie movie;
    private LowCostVideo xGetter;
    private ProgressDialog pb;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding= DataBindingUtil.setContentView(this,R.layout.single_movie_detail);

        pb=new ProgressDialog(this);
        pb.setMessage("Loading movie .....");
        pb.setCancelable(false);


        xGetter= new LowCostVideo(SingleMovieDetail.this);
        xGetter.onFinish(new LowCostVideo.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<XModel> vidURL, boolean multiple_quality) {
                pb.dismiss();
                ChooseQuality(vidURL);
            }

            @Override
            public void onError() {
                Toast.makeText(SingleMovieDetail.this, "error", Toast.LENGTH_SHORT).show();
                pb.dismiss();
            }
        });

        dataBinding.setMovie(movie);
        dataBinding.movieDescription.setShowingLine(7);
        dataBinding.movieDescription.addShowMoreText("Show More");
        dataBinding.movieDescription.addShowLessText("Less");

        dataBinding.backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        dataBinding.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dataBinding.watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.show();
                xGetter.find(movie.getMovieLink());
//                Toast.makeText(SingleMovieDetail.this, "click view", Toast.LENGTH_SHORT).show();
//
//                startActivity(PlayerActivity.gotoPlayerActivity(SingleMovieDetail.this,movie.getMovieLink()));
            }
        });

    }
    public void ChooseQuality(final ArrayList<XModel> vidURL) {

        binding = ChooseQualityLayoutBinding.inflate(LayoutInflater.from(this));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(binding.getRoot());

        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ArrayAdapter<XModel> adapter = new ArrayAdapter<XModel>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, vidURL);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        url=vidURL.get(0).getUrl();
        binding.spinner.setSelection(0);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               url=vidURL.get(position).getUrl();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(PlayerActivity.gotoPlayerActivity(SingleMovieDetail.this,url));
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog = builder.show();

    }
    public static Intent gotoSingleMovieDetail(Context context, Movie mMovie){
        movie=mMovie;
        return new Intent(context,SingleMovieDetail.class);
    }
}