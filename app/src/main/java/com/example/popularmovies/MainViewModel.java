package com.example.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.Utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainViewModel extends ViewModel {

    public MutableLiveData<String> jsonReturn;

    public MutableLiveData<String>getJsonReturn(String sortBy) {
        if (jsonReturn == null) {
            jsonReturn = new MutableLiveData<>();
            createJsonReturn(sortBy);
        }
        return jsonReturn;
    }

    public void createJsonReturn(String sortBy){
        URL githubSearchUrl = NetworkUtils.buildUrl(sortBy);
        new GithubQueryTask().execute(githubSearchUrl);
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String githubSearchResults = null;
            String string = searchUrl.toString();
            Log.d("build", "doInBackground: " + string);
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
