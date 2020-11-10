package com.stone.moviechannel.dao;

import com.stone.moviechannel.data.Movie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MovieDao {
    @Query("select * from movie order by id desc")
    List<Movie> getAllMovie();

    @Query("select * from movie where movieType='action' order by id desc")
    List<Movie> getAction();

    @Query("select * from movie where movieType='drama' order by id desc")
    List<Movie> getDrama();

    @Insert
    void updateAll(Movie movies);

    @Query("DELETE FROM movie")
    public void NullTable();

}
