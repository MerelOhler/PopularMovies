package com.example.popularmovies.Utils;

import android.media.Rating;
import android.util.Log;

import com.example.popularmovies.MovieToShow;
import com.example.popularmovies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class JSONUtils {
    private static String RESULTS_KEY = "results";
    private static String TITLE_KEY = "original_title";
    private static String POSTER_URL_KEY = "poster_path";
    private static String SYNOPSIS_KEY = "overview";
    private static String RATING_KEY = "vote_average";
    private static String RELEASEDATE_KEY = "release_date";

    public static ArrayList<MovieToShow> parseMovies (String json){

        ArrayList<MovieToShow> moviesToShow = new ArrayList<>();
        String moviePosterUrl;
        String originalTitle;
        String synopsis;
        String rating;
        String releaseDate;

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray(RESULTS_KEY);
            for(int i = 0; i < results.length();i++){
                JSONObject movie = results.getJSONObject(i);
                originalTitle = getResult(TITLE_KEY,movie);
                moviePosterUrl = getResult(POSTER_URL_KEY,movie);
                synopsis = getResult(SYNOPSIS_KEY,movie);
                rating = getResult(RATING_KEY,movie);
                releaseDate = getResult(RELEASEDATE_KEY,movie);
                Log.d("JSONUtils", "parseMovies: " + originalTitle + " " + moviePosterUrl
                + " " + synopsis + " " + rating + " " + releaseDate);
                moviesToShow.add(new MovieToShow(moviePosterUrl,originalTitle,synopsis,rating,releaseDate));
            }

            return moviesToShow;

        }catch (JSONException err){
        Log.d("Error", err.toString());
        return null;
        }
    }

    private static String getResult(String key, JSONObject movie) throws JSONException {
        return movie.has(key) ? movie.getString(key) : "Currently unavailable";
    }

}
