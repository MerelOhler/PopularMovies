package com.example.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterAdapterViewHolder> {

    private ArrayList<MovieToShow> mMovieData;

    private final MoviePosterAdapterOnClickHandler mClickHandler;
    private Context mContext;

    /**
     * The interface that receives onClick messages.
     */
    public interface MoviePosterAdapterOnClickHandler {
        void onClick(MovieToShow currentMovie);
    }

    /**
     * Creates a ForecastAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MoviePosterAdapter(MoviePosterAdapterOnClickHandler clickHandler, Context context) {
        mClickHandler = clickHandler;
        mContext = context;
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public class MoviePosterAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mMoviePosterIV;
        private final TextView mMovieUnavailableTV;
        private final ProgressBar mProgressBar;

        private MoviePosterAdapterViewHolder(View view) {
            super(view);
            mMoviePosterIV = view.findViewById(R.id.movie_poster_iv);
            mMovieUnavailableTV = view.findViewById(R.id.movie_poster_unavailable);
            mProgressBar = view.findViewById(R.id.movie_poster_pb);
            view.setOnClickListener(this);
        }

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
    public void onBindViewHolder(final MoviePosterAdapterViewHolder moviePosterAdapterViewHolder, int position) {
        MovieToShow currentMovie = mMovieData.get(position);
        String posterURL = currentMovie.getMoviePosterUrl();
        String posterUnavailable = mContext.getString(R.string.poster_for) + " " +
                currentMovie.getOriginalTitle() + " " + mContext.getString(R.string.is_currently_unavailable);
        moviePosterAdapterViewHolder.mProgressBar.setVisibility(View.VISIBLE);
        moviePosterAdapterViewHolder.mMoviePosterIV.setVisibility(View.GONE);
        if (currentMovie.isHasPicture()){
            moviePosterAdapterViewHolder.mMovieUnavailableTV.setVisibility(View.GONE);
            moviePosterAdapterViewHolder.mMoviePosterIV.setVisibility(View.VISIBLE);
            Picasso.get().load(posterURL).into(moviePosterAdapterViewHolder.mMoviePosterIV, new Callback() {
                @Override
                public void onSuccess() {
                    moviePosterAdapterViewHolder.mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }else{
            moviePosterAdapterViewHolder.mMovieUnavailableTV.setVisibility(View.VISIBLE);
            moviePosterAdapterViewHolder.mMoviePosterIV.setVisibility(View.GONE);
            moviePosterAdapterViewHolder.mMovieUnavailableTV.setText(posterUnavailable);
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
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}