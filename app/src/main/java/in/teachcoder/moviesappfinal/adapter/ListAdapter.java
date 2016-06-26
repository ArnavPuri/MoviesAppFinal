package in.teachcoder.moviesappfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.teachcoder.moviesappfinal.R;
import in.teachcoder.moviesappfinal.activity.DetailActivity;
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
                .resize(500, 750)
                .placeholder(c.getResources().getDrawable(R.drawable.placeholder))
                .into(holder.moviePoster);
    }


    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView movieTitle;
        TextView movieStatus;
        ImageView moviePoster;


        public ViewHolder(View v) {
            super(v);
            this.movieTitle = (TextView) v.findViewById(R.id.movie_title);
            this.movieStatus = (TextView) v.findViewById(R.id.movie_status);
            this.moviePoster = (ImageView) v.findViewById(R.id.movie_poster);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MovieItem clickedItem = mMovies.get(getAdapterPosition());
            Intent i = new Intent(c, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("clicked_item",clickedItem);
            i.putExtras(bundle);
            i.putExtra("movie_id",clickedItem.getId());
            c.startActivity(i);
            Log.d("ListAdapter", getAdapterPosition() + " ");
        }
    }
}
