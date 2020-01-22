public class Movie {
    private String moviePosterUrl;
    private String originalTitle;
    private String synopsis;
    private String rating;
    private String realeaseDate;

    public Movie(String moviePosterUrl, String originalTitle, String synopsis, String rating, String realeaseDate) {
        this.moviePosterUrl = moviePosterUrl;
        this.originalTitle = originalTitle;
        this.synopsis = synopsis;
        this.rating = rating;
        this.realeaseDate = realeaseDate;
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

    public String getRealeaseDate() {
        return realeaseDate;
    }

    public void setRealeaseDate(String realeaseDate) {
        this.realeaseDate = realeaseDate;
    }
}
