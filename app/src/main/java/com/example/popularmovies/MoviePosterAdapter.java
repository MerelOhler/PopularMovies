package com.example.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterAdapterViewHolder> {

    private ArrayList<MovieToShow> mMovieData;

    private final MoviePosterAdapterOnClickHandler mClickHandler;
    private final String TAG = "MoviePosterAdapter";

    /**
     * The interface that receives onClick messages.
     */
    public interface MoviePosterAdapterOnClickHandler {
        void onClick(MovieToShow currentMovie);
    }

    // COMPLETED (4) Add a ForecastAdapterOnClickHandler as a parameter to the constructor and store it in mClickHandler
    /**
     * Creates a ForecastAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MoviePosterAdapter(MoviePosterAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    // COMPLETED (5) Implement OnClickListener in the ForecastAdapterViewHolder class
    /**
     * Cache of the children views for a forecast list item.
     */
    public class MoviePosterAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMoviePosterIV;
        public final TextView mMovieUnavailableTV;

        public MoviePosterAdapterViewHolder(View view) {
            super(view);
            Log.d(TAG, "MoviePosterAdapterViewHolder: ");
            mMoviePosterIV = view.findViewById(R.id.movie_poster_iv);
            mMovieUnavailableTV = view.findViewById(R.id.movie_poster_unavailable);
            // COMPLETED (7) Call setOnClickListener on the view passed into the constructor (use 'this' as the OnClickListener)
            view.setOnClickListener(this);
        }

        // COMPLETED (6) Override onClick, passing the clicked day's data to mClickHandler via its onClick method
        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieToShow currentMovie = mMovieData.get(adapterPosition);
            mClickHandler.onClick(currentMovie);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     * @return A new ForecastAdapterViewHolder that holds the View for each list item
     */
    @Override
    public MoviePosterAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_poster_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviePosterAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param moviePosterAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MoviePosterAdapterViewHolder moviePosterAdapterViewHolder, int position) {
        MovieToShow currentMovie = mMovieData.get(position);
        String posterURL = currentMovie.getMoviePosterUrl();
        Log.d(TAG, "onBindViewHolder: " +posterURL);
        if (currentMovie.isHasPicture()){
            moviePosterAdapterViewHolder.mMovieUnavailableTV.setVisibility(View.GONE);
            moviePosterAdapterViewHolder.mMoviePosterIV.setVisibility(View.VISIBLE);
            Picasso.get().load(posterURL).placeholder(R.mipmap.ic_launcher).into(moviePosterAdapterViewHolder.mMoviePosterIV);
        }else{
            moviePosterAdapterViewHolder.mMovieUnavailableTV.setVisibility(View.VISIBLE);
            moviePosterAdapterViewHolder.mMoviePosterIV.setVisibility(View.GONE);
            moviePosterAdapterViewHolder.mMovieUnavailableTV.setText("Poster for " + currentMovie.getOriginalTitle() + " is currently unavailable");
        }
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + mMovieData.size());
        if (null == mMovieData) return 0;
        return mMovieData.size();
    }

    /**
     * This method is used to set the weather forecast on a ForecastAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new ForecastAdapter to display it.
     *
     * @param movieData The new weather data to be displayed.
     */
    public void setMoviePosterData(ArrayList<MovieToShow> movieData) {
        Log.d(TAG, "setMoviePosterData: ");
        mMovieData = movieData;
        Log.d(TAG, "setMoviePosterData: " + mMovieData.size());
        notifyDataSetChanged();
    }
}