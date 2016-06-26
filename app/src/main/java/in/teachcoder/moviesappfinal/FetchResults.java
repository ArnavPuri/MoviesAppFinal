package in.teachcoder.moviesappfinal;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.teachcoder.moviesappfinal.model.MovieItem;

/**
 * Created by Arnav on 25-Jun-16.
 */
public class FetchResults extends AsyncTask<String, Void, ArrayList<MovieItem>> {
    Uri buildUri;
    URL url;
    String JSONstr;
    ArrayList<MovieItem> movieItems = new ArrayList<>();
    Context context;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    public FetchResults(Context context, RecyclerView rv, ProgressBar bar) {
        super();
        this.context = context;
        recyclerView = rv;
        progressBar = bar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected ArrayList<MovieItem> doInBackground(String... params) {
        if (params == null) {
            return null;
        }
        String whichTab = params[0];
        String searchQuery = params[1];

        HttpURLConnection client = null;
        JSONstr = null;
        BufferedReader reader = null;

        final String BASE_URL = "http://api.themoviedb.org/3/";
        final String MOVIE_SEGMENT = "movie";
        final String API_KEY = "9b153f4e40437e115298166e6c1b997c";
        final String API_KEY_PARAM = "api_key";
        final String SEARCH_PARAM = "query";
        final String DISCOVER = "discover";
        final String SEARCH = "search";
        final String UPCOMING = "upcoming";

        switch (whichTab){
            case "Discover":
                buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(DISCOVER)
                        .appendPath(MOVIE_SEGMENT)
                        .appendQueryParameter(API_KEY_PARAM, API_KEY)
                        .build();
                break;
            case "Search":
                buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(SEARCH)
                        .appendPath(MOVIE_SEGMENT)
                        .appendQueryParameter(API_KEY_PARAM, API_KEY)
                        .appendQueryParameter(SEARCH_PARAM, searchQuery)
                        .build();
        }


        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("FetchResults", url.toString());
        try {
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("GET");
            client.connect();
            InputStream inputStream = client.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            //building the json data with string builder
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            JSONstr = builder.toString();
            Log.d("FetchResults", JSONstr);
            return getJSON(JSONstr);

        } catch (IOException | ParseException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private ArrayList<MovieItem> getJSON(String data) throws JSONException, ParseException {
        final String RESULT_ARRAY = "results";
        final String MOVIE_TITLE = "original_title";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";
        final String STATUS = "status";
        final String RATING = "vote_average";
        final String POSTER_URL = "poster_path";
        final String BACKDROP_URL = "backdrop_path";
        final String ID = "id";

        JSONObject jsonData = new JSONObject(data);
        JSONArray resultArray = jsonData.getJSONArray(RESULT_ARRAY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String status;
        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject movieItem = resultArray.getJSONObject(i);
            String movieTitle = movieItem.getString(MOVIE_TITLE);
            String releaseDate = movieItem.getString(RELEASE_DATE);
            String overview = movieItem.getString(OVERVIEW);
            double rating = movieItem.getDouble(RATING);
            String posterURL = movieItem.getString(POSTER_URL);
            String backdropURL = movieItem.getString(BACKDROP_URL);
            int id = movieItem.getInt(ID);
            Date releasedOn = sdf.parse(releaseDate);
            if ((releasedOn.compareTo(date)) <= 0) {
                status = "Released";
            } else {
                status = "Upcoming";
            }
            movieItems.add(new MovieItem(movieTitle, releaseDate, overview, "https://image.tmdb.org/t/p/original" + posterURL, "https://image.tmdb.org/t/p/original" +backdropURL,
                    status, id, rating));
        }
        return movieItems;

    }

    @Override
    protected void onPostExecute(ArrayList<MovieItem> movieItems) {
        super.onPostExecute(movieItems);
        if (movieItems != null){
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            ListAdapter myAdapter = new ListAdapter(movieItems,context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);
            progressBar.setVisibility(View.GONE);

        }
    }
}
