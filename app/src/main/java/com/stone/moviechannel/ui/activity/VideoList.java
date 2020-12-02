package com.stone.moviechannel.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.stone.moviechannel.R;
import com.stone.moviechannel.adapter.MovieAdapter;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.databinding.ActivityVideoListBinding;
import com.stone.moviechannel.listener.GetAllMovie;
import com.stone.moviechannel.listener.onClickMovie;
import com.stone.moviechannel.model.AppModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;

public class VideoList extends AppCompatActivity implements onClickMovie, GetAllMovie {

    private ActivityVideoListBinding binding;
    private MovieAdapter adapter;
    private static String videoType;
    private static List<Movie> movies;
    private AppModel appModel;

    public static Intent goVideoList(Context context, String video) {
        videoType=video;
        return new Intent(context,VideoList.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoListBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appModel=AppModel.getINSTANCE(this);

        adapter = new MovieAdapter(this, this);
        if (videoType==null) {
            adapter.setMovieList(movies);
        }else {
            appModel.getAll(this);
        }

        binding.listRv.setLayoutManager(new GridLayoutManager(this, 3));
        binding.listRv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        MenuItem menuItemSearch = menu.findItem(R.id.list_search);
        SearchView searchView = (SearchView) menu.findItem(R.id.list_search).getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
        menuItemSearch.expandActionView();
        menuItemSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                onBackPressed();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void clickMovie(Movie movie) {
        startActivity(SingleMovieDetail.gotoSingleMovieDetail(this,movie));
    }

    public static Intent gotoVideoList(Context context, List<Movie> movieList) {
        movies = movieList;
        return new Intent(context, VideoList.class);
    }

    @Override
    public void getAllMovie(List<Movie> movies) {
        adapter.setMovieList(movies);
    }

    @Override
    public void fail(String message) {

    }
}