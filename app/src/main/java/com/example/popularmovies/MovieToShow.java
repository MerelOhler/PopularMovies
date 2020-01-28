package com.example.popularmovies;

public class MovieToShow {
    private final String moviePosterUrl;
    private final String originalTitle;
    private final String synopsis;
    private final String rating;
    private final String releaseDate;
    private boolean hasPicture = true;

    public MovieToShow(String moviePosterUrl, String originalTitle, String synopsis, String rating, String releaseDate) {
        if(moviePosterUrl.equals("Currently unavailable")){
            hasPicture = false;
            this.moviePosterUrl = moviePosterUrl;

        }else{
            this.moviePosterUrl = "https://image.tmdb.org/t/p/w185/" + moviePosterUrl;}
            this.originalTitle = originalTitle;
            this.synopsis = synopsis;
            this.rating = rating;
            this.releaseDate = releaseDate;
    }

    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public boolean isHasPicture() {
        return hasPicture;
    }
}
