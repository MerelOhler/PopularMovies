package com.example.popularmovies;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainViewModel extends ViewModel {

    public MutableLiveData<String> jsonReturn;

    public MutableLiveData<String>getJsonReturn(String githubQuery) {
        if (jsonReturn == null) {
            jsonReturn = new MutableLiveData<>();
            URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
            new GithubQueryTask().execute(githubSearchUrl);
        }
        return jsonReturn;
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubSearchResults;
        }

        @Override
        protected void onPostExecute(String movieDBResults) {
            if (movieDBResults != null && !movieDBResults.equals("")) {
                jsonReturn.postValue(movieDBResults);
            }
        }
    }
}