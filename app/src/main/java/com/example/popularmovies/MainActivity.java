package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.Utils.JSONUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String MOST_POPULAR_KEY = "popularity.desc";
    static final String HIGHEST_RATED_KEY = "vote_average.desc";
    private String url = MOST_POPULAR_KEY;
    private TextView textView;
    MainViewModel model;
    LiveData<String> movieDBResult;
    ArrayList<MovieToShow> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textview);
        Picasso.get().setIndicatorsEnabled(true);
        Picasso.get().load("https://www.tate.org.uk/art/images/work/T/T05/T05010_10.jpg").into(imageView);

        model = ViewModelProviders.of(this).get(MainViewModel.class);
        movieDBResult = model.getJsonReturn(url);
        movieDBResult.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
                ArrayList<MovieToShow> moviesToShow = JSONUtils.parseMovies(s);
            }
        });
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
