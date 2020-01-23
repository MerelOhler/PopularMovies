package com.example.popularmovies;

public class MovieToShow {
    private String moviePosterUrl;
    private String originalTitle;
    private String synopsis;
    private String rating;
    private String releaseDate;
    private boolean hasPicture = true;

    public MovieToShow(String moviePosterUrl, String originalTitle, String synopsis, String rating, String releaseDate) {
        if(moviePosterUrl == "null"){
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

    public void setMoviePosterUrl(String moviePosterUrl) {
        this.moviePosterUrl = moviePosterUrl;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isHasPicture() {
        return hasPicture;
    }
}
