package in.teachcoder.moviesappfinal.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import in.teachcoder.moviesappfinal.FetchResults;
import in.teachcoder.moviesappfinal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    EditText searchField;
    Button searchBtn;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.search_recycler_list);
        progressBar = (ProgressBar) v.findViewById(R.id.movie_progress);
        searchField = (EditText) v.findViewById(R.id.search_movie);
        searchBtn = (Button) v.findViewById(R.id.search_movie_btn);
        recyclerView.setHasFixedSize(true);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchResults results = new FetchResults(getContext(), recyclerView, progressBar);
                results.execute("Search", searchField.getText().toString());
            }
        });

        return v;
    }

}
