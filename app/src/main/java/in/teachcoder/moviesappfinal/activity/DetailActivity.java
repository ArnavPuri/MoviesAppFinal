package in.teachcoder.moviesappfinal.activity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import in.teachcoder.moviesappfinal.R;
import in.teachcoder.moviesappfinal.model.MovieItem;
import in.teachcoder.moviesappfinal.model.MovieTrailer;

public class DetailActivity extends AppCompatActivity {
    MovieItem item;
    int movieId;
    TextView overview, releaseDate, ratingText;
    ImageView poster, backdrop;
    RatingBar rating;
    CollapsingToolbarLayout collapseToolbar;
    boolean flag = true;
    Toolbar toolbar;
    LinearLayout titleContainer;
    CardView overviewCard;
    FloatingActionButton fab;
    CoordinatorLayout.Behavior<ImageView> behaviour;
    AsyncTask<String, Void, ArrayList<MovieTrailer>> trailerTask;

    //    Animation scaleUp, scaleDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        item = (MovieItem) getIntent().getExtras().getSerializable("clicked_item");
        initializeViews();
        final ArrayList<MovieTrailer> trailers = trailerFetch(item.getId());
        //      scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        //      scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);
        behaviour = new CoordinatorLayout.Behavior<ImageView>() {

            @Override
            public boolean layoutDependsOn(CoordinatorLayout parent,
                                           ImageView child, View dependency) {
                return dependency instanceof AppBarLayout;
            }

            @Override
            public boolean onDependentViewChanged(CoordinatorLayout parent, final ImageView child, View dependency) {
//                child.setY(dependency.getBottom() - 120);
                Log.d("Dependency", dependency.getBottom() + " | " + dependency.getY() + " | " + child.getY());
                if (dependency.getBottom() <= 300 && flag) {
                    rating.animate()
                            .scaleY(1.0f)
                            .scaleX(1.0f)
                            .setDuration(400)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    rating.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    flag = true;
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                    child.animate()
                            .scaleY(0.0f)
                            .scaleX(0.0f)
                            .setDuration(600)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    child.setVisibility(View.GONE);
                                    flag = false;
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                } else if (!flag && dependency.getBottom() > 300) {
// Part you requirechild.animate()
                    rating.animate()
                            .scaleY(0.0f)
                            .scaleX(0.0f)
                            .setDuration(600)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    rating.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                    child.animate()
                            .scaleY(1.0f)
                            .scaleX(1.0f)
                            .setDuration(400)
                            .setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    child.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    flag = true;
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });

                }
                return true;
            }


        };

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) poster.getLayoutParams();
        params.setBehavior(behaviour);
        rating.setRating((float) item.getRating());
        overview.setText(item.getOverview());
        collapseToolbar.setTitle(" ");
        toolbar.setTitle(item.getTitle());
//        movieTitle.setText(item.getTitle());
        releaseDate.setText(item.getReleaseDate());
        ratingText.setText(item.getRating() + "");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(item.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(item.getTitle());
        //Palette code
        Picasso.with(this).load(item.getBackdropURL())
                .resize(640, 360)
                .into(backdrop);
        Target myTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                backdrop.setImageBitmap(bitmap);
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch bgSwatch = palette.getMutedSwatch();
                        Palette.Swatch darkBgSwatch = palette.getDarkMutedSwatch();

                        Palette.Swatch vibSwatch = palette.getVibrantSwatch();
                        Palette.Swatch darkVibSwatch = palette.getDarkVibrantSwatch();
                        if (bgSwatch == null && vibSwatch == null) {
                            Toast.makeText(DetailActivity.this, "Empty Swatch bro", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (vibSwatch != null && darkVibSwatch != null) {
                            collapseToolbar.setContentScrimColor(vibSwatch.getRgb());
                            collapseToolbar.setCollapsedTitleTextColor(darkVibSwatch.getTitleTextColor());
                            titleContainer.setBackgroundColor(vibSwatch.getRgb());
                            overviewCard.setCardBackgroundColor(darkVibSwatch.getRgb());
                            fab.setBackgroundTintList(ColorStateList.valueOf(vibSwatch.getRgb()));

                            Toast.makeText(DetailActivity.this, "Vib Swatch bro", Toast.LENGTH_SHORT).show();

                        } else if (bgSwatch != null && darkBgSwatch != null) {
                            collapseToolbar.setContentScrimColor(bgSwatch.getRgb());
                            collapseToolbar.setCollapsedTitleTextColor(darkBgSwatch.getTitleTextColor());
                            titleContainer.setBackgroundColor(bgSwatch.getRgb());
                            fab.setBackgroundTintList(ColorStateList.valueOf(darkBgSwatch.getRgb()));
                            overviewCard.setCardBackgroundColor(darkBgSwatch.getRgb());
                            // Empty star
                            Toast.makeText(DetailActivity.this, "BG Swatch bro", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailActivity.this, TrailerActivity.class);
                i.putExtra("trailerKey", trailers.get(0).getTrailerUrl());
                Log.d("trailerFetch", trailers.get(0).getTrailerUrl());
                startActivity(i);
            }
        });
        Picasso.with(this).load(item.getBackdropURL())
                .resize(640, 360)
                .into(myTarget);

        Picasso.with(this).load(item.getPosterURL())
                .resize(120, 180)
                .into(poster);


    }

    public void initializeViews() {
        titleContainer = (LinearLayout) findViewById(R.id.title_container);
        poster = (ImageView) findViewById(R.id.movie_poster);
        backdrop = (ImageView) findViewById(R.id.movie_backdrop);
        overview = (TextView) findViewById(R.id.movie_overview);
        rating = (RatingBar) findViewById(R.id.movie_rating);
        collapseToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        releaseDate = (TextView) findViewById(R.id.release_date);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        ratingText = (TextView) findViewById(R.id.movie_rating_text);
        overviewCard = (CardView) findViewById(R.id.overview_card);
    }

    public ArrayList<MovieTrailer> trailerFetch(final int id) {
        final ArrayList<MovieTrailer> trailers = new ArrayList<>();
        trailerTask = new AsyncTask<String, Void, ArrayList<MovieTrailer>>() {
            URL url;
            Uri buildUri;


            @Override
            protected ArrayList<MovieTrailer> doInBackground(String... strings) {

                HttpURLConnection client = null;
                String JSONstr = null;
                BufferedReader reader = null;

                final String BASE_URL = "http://api.themoviedb.org/3/";
                final String MOVIE_SEGMENT = "movie";
                final String API_KEY = "9b153f4e40437e115298166e6c1b997c";
                final String API_KEY_PARAM = "api_key";
                final String VIDEO_SEGMENT = "videos";

                buildUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(MOVIE_SEGMENT)
                        .appendPath(String.valueOf(id))
                        .appendPath(VIDEO_SEGMENT)
                        .appendQueryParameter(API_KEY_PARAM, API_KEY)
                        .build();

                try {
                    url = new URL(buildUri.toString());
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

                    JSONObject jsonData = new JSONObject(JSONstr);
                    JSONArray resultArray = jsonData.getJSONArray("results");
                    for (int i = 0; i < resultArray.length(); i++) {
                        String trailerKey = resultArray.getJSONObject(i).getString("key");
                        String trailerName = resultArray.getJSONObject(i).getString("name");
                        trailers.add(new MovieTrailer(trailerKey, trailerName));
                    }
                    Log.d("trailerFetch", "  trailer fetched");
                    return trailers;

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                Log.d("trailerFetch", " No trailer fetched");
                return null;
            }
        };

        trailerTask.execute();

        return trailers;
    }

//    private boolean updatePosterVisibility(CoordinatorLayout parent,
//                                           AppBarLayout appBarLayout,
//                                           ImageView posterChild){
//        final CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) posterChild.getLayoutParams();
//        Rect mTmpRect = new Rect();
//
//
//        final Rect rect = mTmpRect;
//        if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
//            // If the anchor's bottom is below the seam, we'll animate our FAB out
//            posterChild.setVisibility(View.GONE);
//        } else {
//            // Else, we'll animate our FAB back in
//            posterChild.setVisibility(View.VISIBLE);
//        }
//    }


}
