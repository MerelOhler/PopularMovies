package com.example.popularmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "extra_name";
    private static final String DEFAULT_NAME = "null";

    public static final String EXTRA_IMAGE_URL = "extra_image_url";
    private static final String DEFAULT_IMAGE_URL = "null";

    public static final String EXTRA_SYNOPSIS = "extra_synopsis";
    private static final String DEFAULT_SYNOPSIS = "null";

    public static final String EXTRA_RATING = "extra_rating";
    private static final String DEFAULT_RATING = "null";

    public static final String EXTRA_RELEASE_DATE = "extra_release_date";
    private static final String DEFAULT_RELEASE_DATE = "null";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        String name = intent.getStringExtra(EXTRA_NAME);
        if (name == DEFAULT_NAME) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        String imageURL = intent.getStringExtra(EXTRA_IMAGE_URL);
        String rating = intent.getStringExtra(EXTRA_RATING);
        String releaseDate = intent.getStringExtra(EXTRA_RELEASE_DATE);
        String synopsis = intent.getStringExtra(EXTRA_SYNOPSIS);

        Log.d("MovieDetailActivity", "onCreate: " + imageURL);

        TextView nameTV = findViewById(R.id.title_detail_tv);
        nameTV.setText(name);
        //setTitle(name);

        ImageView moviePosterIV = findViewById(R.id.movie_poster_detail_iv);
        if (imageURL!= null){
            Picasso.get().load(imageURL).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(moviePosterIV);
        }

        TextView ratingTV = findViewById(R.id.rating_detail_tv);
        ratingTV.setText(rating);

        TextView releaseDateTV = findViewById(R.id.release_date_detail_tv);
        releaseDateTV.setText(releaseDate);

        TextView synopsisTV = findViewById(R.id.synopsis_detail_tv);
        synopsisTV.setText(synopsis);


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
