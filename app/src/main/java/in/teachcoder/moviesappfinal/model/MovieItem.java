package in.teachcoder.moviesappfinal.model;

import java.io.Serializable;

/**
 * Created by Arnav on 25-Jun-16.
 */
public class MovieItem implements Serializable {
    String title, releaseDate, overview, posterURL, backdropURL, status;
    int id;
    double rating;

    public MovieItem(String title, String releaseDate, String overview, String posterURL,
                     String backdropURL, String status, int id, double rating) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterURL = posterURL;
        this.backdropURL = backdropURL;
        this.status = status;
        this.id = id;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getBackdropURL() {
        return backdropURL;
    }

    public void setBackdropURL(String backdropURL) {
        this.backdropURL = backdropURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
