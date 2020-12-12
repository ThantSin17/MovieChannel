package com.stone.moviechannel.database;

import com.stone.moviechannel.dao.MovieDao;
import com.stone.moviechannel.data.Movie;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Movie.class,version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
