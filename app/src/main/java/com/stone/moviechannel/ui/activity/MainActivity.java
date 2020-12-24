package com.stone.moviechannel.ui.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stone.moviechannel.R;
import com.stone.moviechannel.adapter.MovieAdapter;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

public class MainActivity extends AppCompatActivity implements onClickMovie, GetAllMovie {

    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private MovieAdapter mAdapter;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private AppModel appModel;
    private DatabaseReference users;
    TextView singIn,singOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        //Google SingIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Initialize Firebase Auth
        mAuth=FirebaseAuth.getInstance();
        users= FirebaseDatabase.getInstance().getReference().child("MovieChannel").child("Users");

        View headerContainer = binding.naviView.getHeaderView(0); // This returns the container layout from your navigation drawer header layout file (e.g., the parent RelativeLayout/LinearLayout in your my_nav_drawer_header.xml file)
         singIn = (TextView)headerContainer.findViewById(R.id.singin);
         singOut = (TextView)headerContainer.findViewById(R.id.singout);

         //onClick SingIn
        singIn.setOnClickListener(view->{
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        //onClick SingOut
        singOut.setOnClickListener(view->{
            mAuth.signOut();
            mGoogleSignInClient.signOut().addOnCompleteListener(this,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            updateUI(null);
                        }
                    });
        });

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
                    case R.id.top_rating:
                        startActivity(VideoList.goVideoList(MainActivity.this,"rating"));
                        binding.drawerLayout.closeDrawers();
                        break;
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
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser!=null){
            singIn.setText(currentUser.getEmail());
        }else{singIn.setText("Sign In");

        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage()+"error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
                            DatabaseReference current_user_db=users.child(user.getUid());
                            current_user_db.child("Username").setValue(user.getDisplayName());
                            current_user_db.child("Image").setValue(user.getPhotoUrl().toString());
                            updateUI(user);
                        }else {

                            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                });
    }
}