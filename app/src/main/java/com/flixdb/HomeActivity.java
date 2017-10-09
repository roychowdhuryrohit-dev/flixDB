package com.flixdb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flixdb.Adapter.MovieAdapter;
import com.flixdb.Data.Movie;
import com.flixdb.Utilities.MovieJsonUtils;
import com.flixdb.Utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private ProgressBar mNetworkLoadProgressBar;
    private TextView mErrorTextView;
    private RecyclerView mMoviesRecyclerView;

    private MovieAdapter mMovieAdapter;

    private static final int SPAN_COUNT = 3;
    private static NetworkUtils.SortType currentSortType;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNetworkLoadProgressBar = (ProgressBar) findViewById(R.id.error_pv);
        mErrorTextView = (TextView) findViewById(R.id.error_tv);
        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.movie_rv);

        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);

        mMoviesRecyclerView.setLayoutManager(layoutManager);
        mMoviesRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mMoviesRecyclerView.setAdapter(mMovieAdapter);

        currentSortType = NetworkUtils.SortType.POPULARITY;

        loadMovieData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.item_popularity:
                currentSortType = NetworkUtils.SortType.POPULARITY;
                loadMovieData();
                return true;
            case R.id.item_rating:
                currentSortType = NetworkUtils.SortType.RATING;
                loadMovieData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("Movie", movie);
        startActivity(intent);
    }

    private void loadMovieData() {
        showMoviesList();
        new FetchMoviesTask().execute(currentSortType);
    }

    private void showMoviesList() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mMoviesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideMoviesList() {
        mErrorTextView.setVisibility(View.VISIBLE);
        mMoviesRecyclerView.setVisibility(View.INVISIBLE);
    }

    private class FetchMoviesTask extends AsyncTask<NetworkUtils.SortType, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mNetworkLoadProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(NetworkUtils.SortType... params) {
            ArrayList<Movie> movieArrayList = null;
            if (params[0] == null) {
                return null;
            }

            try {
                URL url = NetworkUtils.buildURL(params[0]);
                String jsonData = NetworkUtils.fetchJsonFromHttpUrl(url);
                movieArrayList = MovieJsonUtils.getMovieArrayListFromJson(jsonData);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return movieArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movieArrayList) {
            mNetworkLoadProgressBar.setVisibility(View.INVISIBLE);
            if (movieArrayList != null) {
                showMoviesList();
                mMovieAdapter.setMovieArrayList(movieArrayList);
            } else {
                hideMoviesList();
            }
        }
    }
}
