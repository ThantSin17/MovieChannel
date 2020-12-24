package com.stone.moviechannel.ui.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.htetznaing.lowcostvideo.LowCostVideo;
import com.htetznaing.lowcostvideo.Model.XModel;
import com.stone.moviechannel.R;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.databinding.ChooseQualityLayoutBinding;
import com.stone.moviechannel.databinding.SingleMovieDetailBinding;
import com.stone.moviechannel.model.AppModel;
import com.stone.moviechannel.ui.fragment.CommentFragment;
import com.stone.moviechannel.utils.FontConverter;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import me.myatminsoe.mdetect.MDetect;
import me.myatminsoe.mdetect.Rabbit;

public class SingleMovieDetail extends AppCompatActivity {

    private SingleMovieDetailBinding dataBinding;
    private ChooseQualityLayoutBinding binding;
    private AlertDialog dialog;
    private static Movie movie;
    private LowCostVideo xGetter;
    private ProgressDialog pb;
    private  FirebaseFirestore db;
    private int view, download = 0;
    private int font = 0;
    private int expandText=0;
    private boolean watch=false;
    private boolean bookmark=false;
    private AppModel appModel;

    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.single_movie_detail);

        db = FirebaseFirestore.getInstance();
        getViewerAndDownload();

        appModel=AppModel.getINSTANCE(this);

        pb = new ProgressDialog(this);
        pb.setMessage("Loading movie .....");
        pb.setCancelable(false);
        bookmark=movie.bookmark;


        //call Comment Fragment
        CommentFragment commentFragment=new CommentFragment(movie.id+"");
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.layout_comment,commentFragment);
        transaction.commit();

        xGetter = new LowCostVideo(SingleMovieDetail.this);
        xGetter.onFinish(new LowCostVideo.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<XModel> vidURL, boolean multiple_quality) {
                pb.dismiss();
                ChooseQuality(vidURL);
            }

            @Override
            public void onError() {
                pb.dismiss();
                Toast.makeText(SingleMovieDetail.this, "Sorry, movie link is dead!!", Toast.LENGTH_SHORT).show();
            }
        });


        dataBinding.setMovie(movie);
        MDetect.INSTANCE.init(this);
        if (movie.bookmark){
            dataBinding.bookmark.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent));
        }

        dataBinding.expandDescription.setOnClickListener(view -> {
            if (expandText==0){
                expandText++;
                dataBinding.expandDescription.setText("Show Less");
                dataBinding.movieDescription.setMaxLines(movie.description.length());
            }else {
                expandText--;
                dataBinding.expandDescription.setText("Show More");
                dataBinding.movieDescription.setMaxLines(7);
            }

        });

        dataBinding.backspace.setOnClickListener(view -> onBackPressed());

        dataBinding.bookmark.setOnClickListener(view -> {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
            if (bookmark){
                bookmark=false;
                Toast.makeText(this, "Removed from bookmark !!!", Toast.LENGTH_SHORT).show();
                dataBinding.bookmark.setColorFilter(ContextCompat.getColor(this,R.color.colorWhite));
            }else {
                bookmark=true;
                Toast.makeText(this, "Saved to bookmark !!!", Toast.LENGTH_SHORT).show();
                dataBinding.bookmark.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent));
            }
            updateBookmark();
        });
        dataBinding.watch.setOnClickListener(view -> {
            watch=true;
            pb.show();
            updateView();
            xGetter.find(movie.movieLink);
        });
        dataBinding.download.setOnClickListener(view -> {
            watch=false;

            updateDownload();
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
            }else {
                pb.show();
                xGetter.find(movie.movieLink);
            }
        });
        dataBinding.layoutChangeFont.setOnClickListener(view -> {
            font++;
            switch (font) {
                case 3:
                    font = 0;
                    changeDescriptionFont(movie.description);
                    break;
                case 2:
                    changeDescriptionFont(FontConverter.zg2uni(movie.description));
                    break;
                case 1:
                    changeDescriptionFont(FontConverter.uni2zg(movie.description));
                    break;
                default:
                    changeDescriptionFont(movie.description);
                    break;
            }

        });
        if (MDetect.INSTANCE.isUnicode()){
            //user is using Unicode
            Toast.makeText(this, "Unicode", Toast.LENGTH_SHORT).show();
            changeDescriptionFont(FontConverter.zg2uni(movie.description));
        }else {
            changeDescriptionFont(movie.description);
        }

    }

    private void changeDescriptionFont(String str) {
        dataBinding.movieDescription.setText(str);
    }

    private void getViewerAndDownload() {
        db.collection("Movie")
                .whereEqualTo("id", movie.id)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {

                        return;
                    }
                    assert value != null;
                    for (QueryDocumentSnapshot snapshot : value) {

                        view = snapshot.getLong("viewer").intValue();
                        download = snapshot.getLong("download").intValue();
                        view_download(view, download);
                    }


                });
    }

    private void updateView() {
        WriteBatch batch1 = db.batch();
        ++view;
        DocumentReference Ref = db.collection("Movie").document(String.valueOf(movie.id));
        batch1.update(Ref, "viewer", Long.valueOf(view));
        batch1.commit();
        dataBinding.movieViewer.setText(String.valueOf(view));
        movie.viewer=view;
        appModel.updateBookmark(movie);

    }

    private void updateBookmark(){
        WriteBatch bmBatch=db.batch();
        DocumentReference reference=db.collection("Movie").document(String.valueOf(movie.id));
        bmBatch.update(reference,"bookmark",bookmark);
        bmBatch.commit();
        movie.bookmark=bookmark;
        appModel.updateBookmark(movie);


    }
    private void updateDownload() {
        WriteBatch batch2 = db.batch();
        download++;
        DocumentReference sfRef = db.collection("Movie").document(String.valueOf(movie.id));
        batch2.update(sfRef, "download", Long.valueOf(download));
        batch2.commit();
        dataBinding.movieDownload.setText(String.valueOf(download));
        movie.download=download;
        appModel.updateBookmark(movie);
    }

    private void view_download(int view, int download) {
        dataBinding.movieDownload.setText(String.valueOf(download));
        dataBinding.movieViewer.setText(String.valueOf(view));
    }

    public void ChooseQuality(final ArrayList<XModel> vidURL) {

        binding = ChooseQualityLayoutBinding.inflate(LayoutInflater.from(this));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(binding.getRoot());

        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ArrayAdapter<XModel> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, vidURL);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        url = vidURL.get(0).getUrl();
        binding.spinner.setSelection(0);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                url = vidURL.get(position).getUrl();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            if (watch) {
                startActivity(PlayerActivity.gotoPlayerActivity(SingleMovieDetail.this, url));
            }else {
                downloadMovie(url);
            }
            dialogInterface.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        dialog = builder.show();

    }

    private void downloadMovie(String url) {
        try {
            String baseFolder = android.os.Environment.getExternalStorageDirectory() + File.separator + "MovieChannel" + File.separator;
            if (!new File(baseFolder).exists()) {
                new File(baseFolder).mkdir();
            }
            String mFilePath = "file://" + baseFolder + "/" + movie.title + ".mp4";
            DownloadManager.Request req = new DownloadManager.Request(Uri.parse(url));
            req.setTitle(movie.title);
            req.setDestinationUri(Uri.parse(mFilePath));
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            DownloadManager manager = (DownloadManager) getSystemService(getApplicationContext().DOWNLOAD_SERVICE);
            manager.enqueue(req);
            Toast.makeText(this, "Download start", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent gotoSingleMovieDetail(Context context, Movie mMovie) {
        movie = mMovie;
        return new Intent(context, SingleMovieDetail.class);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==101){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission true", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "reject", Toast.LENGTH_SHORT).show();
            }
        }
    }
}