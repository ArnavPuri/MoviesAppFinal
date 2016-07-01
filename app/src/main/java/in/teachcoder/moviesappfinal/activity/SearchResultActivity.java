package in.teachcoder.moviesappfinal.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;

import in.teachcoder.moviesappfinal.FetchResults;
import in.teachcoder.moviesappfinal.R;

public class SearchResultActivity extends AppCompatActivity {
    String query = "";
    RecyclerView moviesList;
    ProgressBar bar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        moviesList = (RecyclerView) findViewById(R.id.recycler_movies);
        bar = (ProgressBar) findViewById(R.id.movie_progress);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            query = getIntent().getStringExtra(SearchManager.QUERY);
        }

        Log.d("SearchActivity", query);
        FetchResults results = new FetchResults(this, moviesList, bar);
        results.execute("Search", query);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            query = getIntent().getStringExtra(SearchManager.QUERY);
        }
        Log.d("SearchActivity", query);
    }


}
