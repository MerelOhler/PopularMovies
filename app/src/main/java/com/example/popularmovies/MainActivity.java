package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.Utils.JSONUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviePosterAdapter.MoviePosterAdapterOnClickHandler {
    static final String MOST_POPULAR_KEY = "popularity.desc";
    static final String HIGHEST_RATED_KEY = "vote_average.desc";
    private String url = MOST_POPULAR_KEY;
    private RecyclerView mRecyclerView;
    private MoviePosterAdapter mMoviePosterAdapter;
    private GridLayoutManager mLayoutManager;
    private ArrayList<MovieToShow> mMoviesToShow;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private TextView textView;
    Context context = this;
    MainViewModel model;
    LiveData<String> movieDBResult;
    ArrayList<MovieToShow> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movieposter);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */
//        mLayoutManager =
//                new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mLayoutManager
                = new GridLayoutManager(this,2);



//        ImageView imageView = findViewById(R.id.imageView);
//        textView = findViewById(R.id.textview);
//        Picasso.get().setIndicatorsEnabled(true);
//        Picasso.get().load("https://www.tate.org.uk/art/images/work/T/T05/T05010_10.jpg").into(imageView);

        model = ViewModelProviders.of(this).get(MainViewModel.class);
        movieDBResult = model.getJsonReturn(url);
        mMoviePosterAdapter = new MoviePosterAdapter(this);
        mRecyclerView.setAdapter(mMoviePosterAdapter);
        Log.d("MoviePoster", "onCreate: before observe");
        movieDBResult.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                textView.setText(s);
                mMoviesToShow = JSONUtils.parseMovies(s);
                mMoviePosterAdapter.setMoviePosterData(mMoviesToShow);
                mRecyclerView.setLayoutManager(mLayoutManager);

                Log.d("MainMoviePoster", "onChanged:  MoviePoster " + mMoviesToShow.get(0).getMoviePosterUrl());
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
                return true;
            case R.id.most_popular_menu:
                model.createJsonReturn(MOST_POPULAR_KEY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
