package com.stone.moviechannel.dao;

import com.stone.moviechannel.data.Movie;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MovieDao {
    @Query("select * from movie where movieType != 'menu' order by id desc")
    List<Movie> getAllMovie();


    @Insert
    void updateAll(Movie movies);

    @Query("DELETE FROM movie")
    public void NullTable();

    @Query("select * from movie where movieType like '%' || :category || '%' order by id desc")
    List<Movie> getMovie(String category);

    @Query("Select * from movie order by id desc")
    List<Movie> searchByViewer();

    @Update
    void updateBookmark(Movie movie);

    @Query("select * from movie where id=:mid")
    Movie getMovieById(int mid);

    @Query("Select * from movie where movieType != 'menu' order by download desc")
    List<Movie> getDownload();

    @Query("Select * from movie where movieType != 'menu' order by viewer desc")
    List<Movie> getViewer();

    @Query("Select * from movie  where bookmark=1 AND movieType != 'menu' ")
    List<Movie> getBookMark();

}
