package com.stone.moviechannel.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.htetznaing.lowcostvideo.LowCostVideo;
import com.htetznaing.lowcostvideo.Model.XModel;
import com.stone.moviechannel.R;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.data.Series;
import com.stone.moviechannel.databinding.ActivitySeriesBinding;
import com.stone.moviechannel.databinding.ChooseQualityLayoutBinding;
import com.stone.moviechannel.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class SeriesActivity extends AppCompatActivity {

    private ActivitySeriesBinding binding;
    private ChooseQualityLayoutBinding layoutBinding;
    private AlertDialog dialog;
    private static Movie movie;
    private LowCostVideo xGetter;
    private ProgressDialog pb;
    private static FirebaseFirestore db;
    private int view,download=0;
    private List<Series> episodeList;

    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_series);
        db= FirebaseFirestore.getInstance();
        getViewerAndDownload();

        pb=new ProgressDialog(this);
        pb.setMessage("Loading movie .....");
        pb.setCancelable(false);

        episodeList= AppUtil.getList(movie.movieLink);
        binding.selectEpisode.setPrompt("Select");
        ArrayAdapter<Series> episodeAdapter=new ArrayAdapter<Series>(this,R.layout.spinner_item,episodeList);
        episodeAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.selectEpisode.setAdapter(episodeAdapter);
        binding.selectEpisode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i!=0){
                    xGetter.find(episodeList.get(i).link);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        xGetter= new LowCostVideo(SeriesActivity.this);
        xGetter.onFinish(new LowCostVideo.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<XModel> vidURL, boolean multiple_quality) {
                pb.dismiss();
                ChooseQuality(vidURL);
            }

            @Override
            public void onError() {
                Toast.makeText(SeriesActivity.this, "error", Toast.LENGTH_SHORT).show();
                pb.dismiss();
            }
        });

        binding.setMovie(movie);


        binding.backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void getViewerAndDownload() {
        db.collection("Movie")
                .whereEqualTo("id",movie.id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error!=null){
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        assert value != null;
                        for (QueryDocumentSnapshot snapshot:value){
                            Toast.makeText(getApplicationContext(), snapshot.getLong("download").intValue()+"", Toast.LENGTH_SHORT).show();
                            view=snapshot.getLong("viewer").intValue();
                            download=snapshot.getLong("download").intValue();
                            view_download(view,download);
                        }



                    }
                });
    }

    private void updateView(){
        WriteBatch batch1=db.batch();
        ++view;
        DocumentReference Ref = db.collection("Movie").document(String.valueOf(movie.id));
        batch1.update(Ref, "viewer",Long.valueOf(view));
        batch1.commit();
        binding.movieViewer.setText(String.valueOf(view));

    }
    private void updateDownload(){
        WriteBatch batch2=db.batch();
        download++;
        DocumentReference sfRef = db.collection("Movie").document(String.valueOf(movie.id));
        batch2.update(sfRef, "download", Long.valueOf(download));
        batch2.commit();
        binding.movieDownload.setText(String.valueOf(download));
    }
    private void view_download(int view,int download){
        binding.movieDownload.setText(String.valueOf(download));
        binding.movieViewer.setText(String.valueOf(view));
    }
    public void ChooseQuality(final ArrayList<XModel> vidURL) {

        layoutBinding = ChooseQualityLayoutBinding.inflate(LayoutInflater.from(this));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(binding.getRoot());

        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ArrayAdapter<XModel> adapter = new ArrayAdapter<XModel>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, vidURL);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        layoutBinding.spinner.setAdapter(adapter);
        url=vidURL.get(0).getUrl();
        layoutBinding.spinner.setSelection(0);
        layoutBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                startActivity(PlayerActivity.gotoPlayerActivity(SeriesActivity.this,url));
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog = builder.show();

    }
    public static Intent gotoSeriesActivity(Context context, Movie mMovie){
        movie=mMovie;
        return new Intent(context,SeriesActivity.class);

    }

}