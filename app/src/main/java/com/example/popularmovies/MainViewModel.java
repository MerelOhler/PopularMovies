package com.example.popularmovies;

import android.content.res.Resources;
import android.os.AsyncTask;

import com.example.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> jsonReturn;

    public MutableLiveData<String>getJsonReturn(String sortBy) {
        if (jsonReturn == null) {
            jsonReturn = new MutableLiveData<>();
            createJsonReturn(sortBy);
        }
        return jsonReturn;
    }

    public void createJsonReturn(String sortBy){
        URL movieDBSearchUrl = NetworkUtils.buildUrl(sortBy);
        new movieDBQueryTask().execute(movieDBSearchUrl);
    }

    private class movieDBQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieDBSearchResults = null;
            try {
                movieDBSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
                movieDBSearchResults = Resources.getSystem().getString(R.string.nothing);
            }
            return movieDBSearchResults;
        }

        @Override
        protected void onPostExecute(String movieDBResults) {
            if (movieDBResults != null && !movieDBResults.equals("")) {
                jsonReturn.postValue(movieDBResults);
            }
        }
    }
}
