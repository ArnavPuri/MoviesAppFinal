package in.teachcoder.moviesappfinal.activity;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import in.teachcoder.moviesappfinal.R;
import in.teachcoder.moviesappfinal.model.MovieItem;

public class DetailActivity extends AppCompatActivity {
    MovieItem item;
    int movieId;
    TextView overview, releaseDate, ratingText;
    ImageView poster;
    RatingBar rating;
    CollapsingToolbarLayout collapseToolbar;
    Toolbar toolbar;
    LinearLayout titleContainer;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        item = (MovieItem) getIntent().getExtras().getSerializable("clicked_item");
        initializeViews();

        rating.setRating((float) item.getRating());
        overview.setText(item.getOverview());
//        movieTitle.setText(item.getTitle());
        releaseDate.setText(item.getReleaseDate());
        ratingText.setText(item.getRating() + "/10");
        setSupportActionBar(toolbar);
        toolbar.setTitle(item.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(item.getTitle());
        //Palette code
        Target myTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                poster.setImageBitmap(bitmap);
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch bgSwatch = palette.getDarkMutedSwatch();
                        Palette.Swatch darkBgSwatch = palette.getDarkMutedSwatch();

                        Palette.Swatch vibSwatch = palette.getVibrantSwatch();
                        Palette.Swatch darkVibSwatch = palette.getDarkVibrantSwatch();
                        if (bgSwatch == null && vibSwatch == null) {
                            Toast.makeText(DetailActivity.this, "Empty Swatch bro", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (bgSwatch != null && darkBgSwatch != null) {
                            collapseToolbar.setContentScrimColor(bgSwatch.getRgb());
                            collapseToolbar.setCollapsedTitleTextColor(darkBgSwatch.getRgb());
                            titleContainer.setBackgroundColor(bgSwatch.getRgb());
                            fab.setBackgroundTintList(ColorStateList.valueOf(darkBgSwatch.getRgb()));
                            overview.setBackgroundColor(darkBgSwatch.getRgb());
                            // Empty star
                            Toast.makeText(DetailActivity.this, "BG Swatch bro", Toast.LENGTH_SHORT).show();

                        } else if (vibSwatch != null && darkVibSwatch != null) {
                            collapseToolbar.setContentScrimColor(vibSwatch.getRgb());
                            collapseToolbar.setCollapsedTitleTextColor(darkVibSwatch.getRgb());
                            titleContainer.setBackgroundColor(vibSwatch.getRgb());
                            overview.setBackgroundColor(darkVibSwatch.getRgb());
                            fab.setBackgroundTintList(ColorStateList.valueOf(vibSwatch.getRgb()));

                            Toast.makeText(DetailActivity.this, "Vib Swatch bro", Toast.LENGTH_SHORT).show();

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

        Picasso.with(this).load(item.getPosterURL())
                .resize(300, 450)
                .into(myTarget);
    }

    public void initializeViews() {
        titleContainer = (LinearLayout) findViewById(R.id.title_container);
        poster = (ImageView) findViewById(R.id.movie_poster);
        overview = (TextView) findViewById(R.id.movie_overview);
        rating = (RatingBar) findViewById(R.id.movie_rating);
        collapseToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        releaseDate = (TextView) findViewById(R.id.release_date);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        ratingText = (TextView) findViewById(R.id.movie_rating_text);
    }
}
