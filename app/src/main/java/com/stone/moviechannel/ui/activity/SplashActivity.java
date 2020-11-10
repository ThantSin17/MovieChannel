package com.stone.moviechannel.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.stone.moviechannel.R;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.databinding.ActivitySplashBinding;
import com.stone.moviechannel.model.AppModel;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private  FirebaseFirestore db;
    private AppModel appModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        appModel=AppModel.getINSTANCE(this);
        final List<Movie> movies=new ArrayList<>();
        Glide.with(this).load("movie.imageLink").placeholder(R.drawable.progress_animation).into(binding.itemImage);
        db.collection("Movie")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            appModel.makeNullTable();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Movie movie = document.toObject(Movie.class);
                                //movies.add(movie);
                                appModel.Add(movie);
                                //Toast.makeText(context, movie.getImageLink(), Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(SplashActivity.this, "finish", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SplashActivity.this,MainActivity.class));

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}