package in.teachcoder.moviesappfinal.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import in.teachcoder.moviesappfinal.FetchResults;
import in.teachcoder.moviesappfinal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_discover, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_movies);
        progressBar = (ProgressBar) v.findViewById(R.id.movie_progress);

        recyclerView.setHasFixedSize(true);

        FetchResults results = new FetchResults(getContext(), recyclerView, progressBar);
        results.execute("Popular", " ");

        return v;
    }

}
