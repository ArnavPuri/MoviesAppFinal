package in.teachcoder.moviesappfinal;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import in.teachcoder.moviesappfinal.model.MovieItem;

/**
 * Created by Arnav on 25-Jun-16.
 */
public class FetchResults extends AsyncTask<String, Void, ArrayList<MovieItem>> {
    Uri buildUri;
    URL url;
    String JSONstr;

    public FetchResults() {
        super();
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
        final String DISCOVER = "discover";

        buildUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(DISCOVER)
                .appendPath(MOVIE_SEGMENT)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

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

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
