package com.stone.moviechannel.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.stone.moviechannel.R;
import com.stone.moviechannel.adapter.ImageSliderAdapter;
import com.stone.moviechannel.adapter.MovieAdapter;
import com.stone.moviechannel.data.ImageSlide;
import com.stone.moviechannel.data.Movie;
import com.stone.moviechannel.databinding.ActivityMainBinding;
import com.stone.moviechannel.listener.GetAllMovie;
import com.stone.moviechannel.listener.onClickMovie;
import com.stone.moviechannel.model.AppModel;
import com.stone.moviechannel.ui.fragment.ActionFragment;
import com.stone.moviechannel.ui.fragment.DramaFragment;
import com.stone.moviechannel.ui.fragment.SeriesFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onClickMovie, GetAllMovie {

    private ActivityMainBinding binding;
    private Handler handler=new Handler();
    private int currentItem=0;
    private List<ImageSlide> imageSlides;
    private ActionBarDrawerToggle toggle;
    private MovieAdapter mAdapter;
    private AppModel appModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        ImageSliderAdapter adapter=new ImageSliderAdapter(this);

        appModel=AppModel.getINSTANCE(this);
        mAdapter=new MovieAdapter(this,this);
        appModel.getAll(this);

        toggle=new ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.naviView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.about:
                        Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                        binding.drawerLayout.closeDrawers();
                        break;
                    case R.id.setting:
                        Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                    default:return false;
                }
                return true;
            }
        });

        binding.imageSlider.setAdapter(adapter);
        imageSlides=new ArrayList<>();

        ImageSlide slide=new ImageSlide();
        slide.setImageUrl("https://1.bp.blogspot.com/-hu8UkD96vUo/XfUqEBvgB6I/AAAAAAAABDg/evxNz84MTQA1NbairvLaKVqYpc-8wnMKACLcBGAsYHQ/s1600/spiderman_homecoming.jpg");
        imageSlides.add(slide);
        ImageSlide slide2=new ImageSlide();
        slide2.setImageUrl("https://1.bp.blogspot.com/-hu8UkD96vUo/XfUqEBvgB6I/AAAAAAAABDg/evxNz84MTQA1NbairvLaKVqYpc-8wnMKACLcBGAsYHQ/s1600/spiderman_homecoming.jpg");
        imageSlides.add(slide2);
        ImageSlide slide3=new ImageSlide();
        slide3.setImageUrl("https://1.bp.blogspot.com/-hu8UkD96vUo/XfUqEBvgB6I/AAAAAAAABDg/evxNz84MTQA1NbairvLaKVqYpc-8wnMKACLcBGAsYHQ/s1600/spiderman_homecoming.jpg");
        imageSlides.add(slide3);
        ImageSlide slide4=new ImageSlide();
        slide4.setImageUrl("https://1.bp.blogspot.com/-hu8UkD96vUo/XfUqEBvgB6I/AAAAAAAABDg/evxNz84MTQA1NbairvLaKVqYpc-8wnMKACLcBGAsYHQ/s1600/spiderman_homecoming.jpg");
        imageSlides.add(slide4);
        ImageSlide slide5=new ImageSlide();

        slide5.setImageUrl("https://1.bp.blogspot.com/-hu8UkD96vUo/XfUqEBvgB6I/AAAAAAAABDg/evxNz84MTQA1NbairvLaKVqYpc-8wnMKACLcBGAsYHQ/s1600/spiderman_homecoming.jpg");
        imageSlides.add(slide5);
        adapter.setImageSlideList(imageSlides);

        new TabLayoutMediator(binding.indicator, binding.imageSlider, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

            }
        }).attach();



        ActionFragment fragAction=new ActionFragment();
        DramaFragment fragDrama=new DramaFragment();
        SeriesFragment fragSeries=new SeriesFragment();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.action_layout,fragAction);
        transaction.add(R.id.drama_layout,fragDrama);
        transaction.add(R.id.series_layout,fragSeries);
        transaction.commit();

        binding.imageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(slider);
                handler.postDelayed(slider,5000);
            }
        });
    }

    private Runnable slider=new Runnable() {
        @Override
        public void run() {
            if (currentItem==imageSlides.size()-1){
                currentItem=0;
                binding.imageSlider.setCurrentItem(currentItem);
            }else {
                binding.imageSlider.setCurrentItem(++currentItem);
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchView searchView= (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setImeOptions(6);
        searchView.setIconified(false);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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