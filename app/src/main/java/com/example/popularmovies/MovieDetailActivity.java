package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "extra_name";
    private static final String DEFAULT_NAME = "null";
    public static final String EXTRA_IMAGE_URL = "extra_image_url";
    public static final String EXTRA_SYNOPSIS = "extra_synopsis";
    public static final String EXTRA_RATING = "extra_rating";
    public static final String EXTRA_RELEASE_DATE = "extra_release_date";
    public static final String EXTRA_HAS_PICTURE = "extra_has_picture";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        final String name = intent.getStringExtra(EXTRA_NAME);
        if (name.equals(DEFAULT_NAME)) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        String imageURL = intent.getStringExtra(EXTRA_IMAGE_URL);
        String rating = intent.getStringExtra(EXTRA_RATING);
        String releaseDate = intent.getStringExtra(EXTRA_RELEASE_DATE);
        String synopsis = intent.getStringExtra(EXTRA_SYNOPSIS);

        //setTitle(name);
         TextView nameTV = findViewById(R.id.title_detail_tv);
        nameTV.setText(name);

        //set image
        final ImageView moviePosterIV = findViewById(R.id.movie_poster_detail_iv);
        final TextView unavailablePosterTV = findViewById(R.id.movie_poster_unavailable_detail_tv);
        Picasso.get().load(imageURL).into(moviePosterIV, new Callback() {
            @Override
            public void onSuccess() {
                moviePosterIV.setVisibility(View.VISIBLE);
                unavailablePosterTV.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                String posterUnavailable =posterUnavailable(name);
                moviePosterIV.setMinimumHeight(250);
                moviePosterIV.setVisibility(View.INVISIBLE);
                unavailablePosterTV.setVisibility(View.VISIBLE);
                unavailablePosterTV.setText(posterUnavailable);

            }
        });

        //set rating
        TextView ratingTV = findViewById(R.id.rating_detail_tv);
        available(ratingTV,rating);

        //set release date
        TextView releaseDateTV = findViewById(R.id.release_date_detail_tv);
        available(releaseDateTV,releaseDate);

        //set synopsis
        TextView synopsisTV = findViewById(R.id.synopsis_detail_tv);
        available(synopsisTV,synopsis);


    }

    private String posterUnavailable(String name) {
        return getString(R.string.poster_for) + " " + name + " " +
                getString(R.string.is_currently_unavailable);
    }


    //makes sure that the JSON provides data back for this string
    private void available(TextView textView, String string){
        if(string.isEmpty()||string.equals("null")){
            textView.setText(R.string.currently_unavailable);
        }else{
            textView.setText(string);
        }
    }

    //if something goes wrong with loading the data
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
