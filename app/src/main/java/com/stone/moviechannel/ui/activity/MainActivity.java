package com.stone.moviechannel.ui.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.stone.moviechannel.R;
import com.stone.moviechannel.adapter.MovieAdapter;
import com.stone.moviechannel.data.ImageSlide;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.databinding.ActivityMainBinding;
import com.stone.moviechannel.listener.GetAllMovie;
import com.stone.moviechannel.listener.onClickMovie;
import com.stone.moviechannel.model.AppModel;
import com.stone.moviechannel.ui.fragment.ActionFragment;
import com.stone.moviechannel.ui.fragment.AnimationFragment;
import com.stone.moviechannel.ui.fragment.ChinaFragment;
import com.stone.moviechannel.ui.fragment.ComedyFragment;
import com.stone.moviechannel.ui.fragment.DramaFragment;
import com.stone.moviechannel.ui.fragment.ImageSliderFragment;
import com.stone.moviechannel.ui.fragment.LatestFragment;
import com.stone.moviechannel.ui.fragment.SeriesFragment;
import com.stone.moviechannel.ui.fragment.SuperHeroFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

public class MainActivity extends AppCompatActivity implements onClickMovie, GetAllMovie {

    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private MovieAdapter mAdapter;
    private AppModel appModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());


        appModel=AppModel.getINSTANCE(this);
        mAdapter=new MovieAdapter(this,this);
        appModel.getAll(this);

        binding.searchLayout.setLayoutManager(new GridLayoutManager(this,3));
        binding.searchLayout.setHasFixedSize(true);
        binding.searchLayout.setAdapter(mAdapter);

        toggle=new ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.naviView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.most_view:
                        startActivity(VideoList.goVideoList(MainActivity.this,"viewer"));
                        binding.drawerLayout.closeDrawers();
                        break;
                    case R.id.most_download:
                        startActivity(VideoList.goVideoList(MainActivity.this,"download"));
                        binding.drawerLayout.closeDrawers();
                        break;
//                    case R.id.top_rating:
//                        startActivity(VideoList.goVideoList(MainActivity.this,"rating"));
//                        binding.drawerLayout.closeDrawers();
//                        break;
                    case R.id.book_mark:
                        startActivity(VideoList.goVideoList(MainActivity.this,"bookmark"));
                        binding.drawerLayout.closeDrawers();
                    case R.id.about:
                        Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                        binding.drawerLayout.closeDrawers();
                        break;
                    case R.id.setting:
                        Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                        binding.drawerLayout.closeDrawers();
                        break;
                    default:return false;
                }
                return true;
            }
        });



        LatestFragment latestFragment=new LatestFragment();
        ActionFragment fragAction=new ActionFragment();
        ComedyFragment comedyFragment=new ComedyFragment();
        DramaFragment fragDrama=new DramaFragment();
        ChinaFragment chinaFragment=new ChinaFragment();
        AnimationFragment animationFragment=new AnimationFragment();
        SuperHeroFragment superHeroFragment=new SuperHeroFragment();
        SeriesFragment fragSeries=new SeriesFragment();
        ImageSliderFragment imageSliderFragment=new ImageSliderFragment();

        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.latest_layout,latestFragment);
        transaction.add(R.id.action_layout,fragAction);
        transaction.add(R.id.comedy_layout,comedyFragment);
        transaction.add(R.id.drama_layout,fragDrama);
        transaction.add(R.id.china_layout,chinaFragment);
        transaction.add(R.id.animation_layout,animationFragment);
        transaction.add(R.id.superhero_layout,superHeroFragment);
        transaction.add(R.id.series_layout,fragSeries);
        transaction.add(R.id.layout_image_slider,imageSliderFragment);
        transaction.commit();


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        Drawable drawable=getResources().getDrawable(R.drawable.ic_search_black_24dp);
        drawable.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        menu.findItem(R.id.app_bar_search).setIcon(drawable);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        MenuItem menuItemSearch = menu.findItem(R.id.app_bar_search);
        SearchView searchView= (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return true;
            }
        });
        menuItemSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                binding.mainLayout.setVisibility(View.GONE);
                binding.searchLayout.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                binding.searchLayout.setVisibility(View.GONE);
                binding.mainLayout.setVisibility(View.VISIBLE);

                return true;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickMovie(Movie movie) {

    }

    @Override
    public void getAllMovie(List<Movie> movies) {
        mAdapter.setMovieList(movies);
    }

    @Override
    public void fail(String message) {

    }
}