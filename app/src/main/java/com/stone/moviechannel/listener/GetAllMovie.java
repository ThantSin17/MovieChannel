package com.stone.moviechannel.listener;

import com.stone.moviechannel.data.Movie;

import java.util.List;

public interface GetAllMovie {
    public void getAllMovie(List<Movie> movies);
    public void fail(String message);
}
