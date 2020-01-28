package com.example.popularmovies.Utils;


import android.util.Log;

import com.example.popularmovies.MovieToShow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {
    private static final String RESULTS_KEY = "results";
    private static final String TITLE_KEY = "original_title";
    private static final String POSTER_URL_KEY = "poster_path";
    private static final String SYNOPSIS_KEY = "overview";
    private static final String RATING_KEY = "vote_average";
    private static final String RELEASE_DATE_KEY = "release_date";

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
                if (results.optJSONObject(i).has(TITLE_KEY)){

                    JSONObject movie = results.getJSONObject(i);
                    originalTitle = getResult(TITLE_KEY,movie);
                    moviePosterUrl = getResult(POSTER_URL_KEY,movie);
                    synopsis = getResult(SYNOPSIS_KEY,movie);
                    rating = getResult(RATING_KEY,movie);
                    releaseDate = getResult(RELEASE_DATE_KEY,movie);
                    moviesToShow.add(new MovieToShow(moviePosterUrl,originalTitle,synopsis,rating,releaseDate));
                }
            }

            return moviesToShow;

        }catch (JSONException err){
        Log.d("Error", err.toString());
        return null;
        }
    }

    private static String getResult(String key, JSONObject movie) throws JSONException {
        if(movie.has(key)&& !movie.getString(key).equals("null")&& !movie.getString(key).isEmpty()){
            return movie.getString(key);
        }
        return "Currently unavailable";
        //how can I hardcode this string?
    }

}
