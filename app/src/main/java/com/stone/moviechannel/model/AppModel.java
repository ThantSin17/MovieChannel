package com.stone.moviechannel.model;

import android.content.Context;

import com.google.firebase.firestore.DocumentReference;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.database.AppDatabase;
import com.stone.moviechannel.listener.GetAllMovie;

import java.util.List;

import androidx.room.Room;

public class AppModel {
    private static AppModel INSTANCE;

    private static AppDatabase db;


    public static AppModel getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AppModel();

            db= Room.databaseBuilder(context,AppDatabase.class,"Movies").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }



    public void Add(Movie movies){
        db.movieDao().updateAll(movies);
    }
    
    public  void getAll(GetAllMovie getAllMovie) {
        try {
            getAllMovie.getAllMovie(db.movieDao().getAllMovie());
        }catch (Exception e){
            getAllMovie.fail(e.getMessage());
        }

    }


    public  void getMovie(final GetAllMovie getAllMovie,String category) {
        try {
            getAllMovie.getAllMovie(db.movieDao().getMovie(category));
        }catch (Exception e){
            getAllMovie.fail(e.getMessage());
        }
    }
    public void makeNullTable(){
        db.movieDao().NullTable();
    }


}
