package in.teachcoder.moviesappfinal.model;

/**
 * Created by Arnav on 26-Jun-16.
 */
public class MovieTrailer {
    String trailerUrl, trailerName;

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public MovieTrailer(String trailerUrl, String trailerName) {

        this.trailerUrl = trailerUrl;
        this.trailerName = trailerName;
    }
}
