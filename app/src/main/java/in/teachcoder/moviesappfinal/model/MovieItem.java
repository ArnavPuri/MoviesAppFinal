package in.teachcoder.moviesappfinal.model;

/**
 * Created by Arnav on 25-Jun-16.
 */
public class MovieItem {
    String title, releaseDate, overview, posterURL, backdropURL, status;
    int id;
    float rating;

    public MovieItem(String title, String releaseDate, String overview, String posterURL,
                     String backdropURL, String status, int id, float rating) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterURL = posterURL;
        this.backdropURL = backdropURL;
        this.status = status;
        this.id = id;
        this.rating = rating;
    }
}
