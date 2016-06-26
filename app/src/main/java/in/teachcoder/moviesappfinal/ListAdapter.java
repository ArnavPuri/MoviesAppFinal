package in.teachcoder.moviesappfinal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.teachcoder.moviesappfinal.model.MovieItem;

/**
 * Created by Arnav on 26-Jun-16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<MovieItem> mMovies;
    private Context c;

    public ListAdapter(ArrayList<MovieItem> movies, Context context) {
        mMovies = movies;
        c = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieItem movie = mMovies.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieStatus.setText(movie.getStatus());
        Picasso.with(c).load(movie.getPosterURL())
                .resize(600,900)
                .placeholder(c.getResources().getDrawable(R.drawable.placeholder))
                .into(holder.moviePoster);
    }


    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        TextView movieStatus;
        ImageView moviePoster;

        public ViewHolder(View v) {
            super(v);
            this.movieTitle = (TextView) v.findViewById(R.id.movie_title);
            this.movieStatus = (TextView) v.findViewById(R.id.movie_status);
            this.moviePoster = (ImageView) v.findViewById(R.id.movie_poster);
        }
    }
}
