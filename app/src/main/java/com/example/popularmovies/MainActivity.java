package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.Utils.JSONUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.MoviePosterAdapterOnClickHandler {
    static private final String MOST_POPULAR_KEY = "popularity.desc";
    static private final String HIGHEST_RATED_KEY = "vote_average.desc";
    private String url = MOST_POPULAR_KEY;
    private RecyclerView mRecyclerView;
    private MoviePosterAdapter mMoviePosterAdapter;
    private GridLayoutManager mLayoutManager;
    private ArrayList<MovieToShow> mMoviesToShow;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private MainViewModel model;
    private LiveData<String> movieDBResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movieposter);

        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        checkForNetwork();

        mLayoutManager
                = new GridLayoutManager(this,2);


        model = ViewModelProviders.of(this).get(MainViewModel.class);
        movieDBResult = model.getJsonReturn(url);
        mMoviePosterAdapter = new MoviePosterAdapter(this,this);
        mRecyclerView.setAdapter(mMoviePosterAdapter);
        movieDBResult.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("main", "onChanged: " + s);
                if (s.equals(getString(R.string.nothing))) {
                    mLoadingIndicator.setVisibility(View.GONE);
                    mErrorMessageDisplay.setVisibility(View.VISIBLE);
                    mErrorMessageDisplay.setText(R.string.error_message);
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    mLoadingIndicator.setVisibility(View.GONE);
                    mErrorMessageDisplay.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mMoviesToShow = JSONUtils.parseMovies(s);
                    if (mMoviesToShow !=null) {
                        mMoviePosterAdapter.setMoviePosterData(mMoviesToShow);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                    }else{
                        mRecyclerView.setVisibility(View.GONE);
                        mErrorMessageDisplay.setVisibility(View.VISIBLE);
                        mErrorMessageDisplay.setText(R.string.error_message);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(MovieToShow currentMovie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_NAME, currentMovie.getOriginalTitle());
        intent.putExtra(MovieDetailActivity.EXTRA_IMAGE_URL, currentMovie.getMoviePosterUrl());
        intent.putExtra(MovieDetailActivity.EXTRA_SYNOPSIS, currentMovie.getSynopsis());
        intent.putExtra(MovieDetailActivity.EXTRA_RATING, currentMovie.getRating());
        intent.putExtra(MovieDetailActivity.EXTRA_RELEASE_DATE, currentMovie.getReleaseDate());
        intent.putExtra(MovieDetailActivity.EXTRA_HAS_PICTURE,currentMovie.isHasPicture());
        //is there a better way to do this?
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.highest_rated_menu:
                model.createJsonReturn(HIGHEST_RATED_KEY);
                checkForNetwork();
                return true;
            case R.id.most_popular_menu:
                model.createJsonReturn(MOST_POPULAR_KEY);
                checkForNetwork();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void checkForNetwork(){
        if (!isNetworkAvailable()){
            mLoadingIndicator.setVisibility(View.GONE);
            mErrorMessageDisplay.setVisibility(View.VISIBLE);
            mErrorMessageDisplay.setText(R.string.no_internet);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

}
