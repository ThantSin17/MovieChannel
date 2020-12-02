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


    @Insert
    void updateAll(Movie movies);

    @Query("DELETE FROM movie")
    public void NullTable();

    @Query("select * from movie where movieType like :category order by id desc")
    List<Movie> getMovie(String category);

    @Query("Select * from movie order by id desc")
    List<Movie> searchByViewer();


}
