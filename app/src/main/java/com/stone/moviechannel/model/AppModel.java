package com.stone.moviechannel.model;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.listener.GetAllMovie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class AppModel {
    private static AppModel INSTANCE;

    private static FirebaseFirestore db;

    public static AppModel getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new AppModel();
            db = FirebaseFirestore.getInstance();
        }
        return INSTANCE;
    }

    public  void getAction(final Context context, final GetAllMovie getAllMovie) {
        final List<Movie> movies = new ArrayList<>();
        db.collection("Action")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Movie movie = document.toObject(Movie.class);
                                movies.add(movie);
                                //Toast.makeText(context, movie.getImageLink(), Toast.LENGTH_SHORT).show();
                            }
                            getAllMovie.getAllMovie(movies);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getAllMovie.fail(e.getMessage());
                    }
                });
    }

    public  void getMovie(final Context context, final GetAllMovie getAllMovie) {
        final List<Movie> movies = new ArrayList<>();
        db.collection("Movie")
                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        if (!queryDocumentSnapshots.isEmpty()){
//                            Toast.makeText(context, queryDocumentSnapshots.get, Toast.LENGTH_SHORT).show();
//                            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
//                                Movie movie=snapshot.toObject(Movie.class);
//                                movies.add(movie);
//                            }
////                            List<Movie> movie=queryDocumentSnapshots.toObjects(Movie.class);
////                            movies.addAll(movie);
//                            getAllMovie.getAllMovie(movies);
//                        }
//                    }
//                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // getAllMovie.fail(e.getMessage());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Movie movie = document.toObject(Movie.class);
                                movies.add(movie);
                                //Toast.makeText(context, movie.getImageLink(), Toast.LENGTH_SHORT).show();
                            }
                            getAllMovie.getAllMovie(movies);
                        }
                    }
                });
    }
}
