package com.flixdb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.flixdb.Data.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    private Movie currentMovie;
    private TextView mTitleTextView;
    private TextView mReleaseDateTextView;
    private TextView mRatingTextView;
    private TextView mSynopsisTextView;
    private ImageView mPosterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mTitleTextView = (TextView) findViewById(R.id.title_tv);
        mReleaseDateTextView = (TextView) findViewById(R.id.release_date_tv);
        mRatingTextView = (TextView) findViewById(R.id.vote_average_tv);
        mSynopsisTextView = (TextView) findViewById(R.id.synopsis_tv);

        mPosterImageView = (ImageView) findViewById(R.id.poster_iv);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null && intentThatStartedThisActivity.hasExtra("Movie")) {
            currentMovie = intentThatStartedThisActivity.getParcelableExtra("Movie");
            if (currentMovie != null) {
                fillLayoutWithData();
            }
        }
    }

    private void fillLayoutWithData() {
        mTitleTextView.setText(currentMovie.getTitle());
        mReleaseDateTextView.setText(getString(R.string.releasing_on, currentMovie.getReleaseDate()));
        mRatingTextView.setText(currentMovie.getVoteAverage());
        mSynopsisTextView.setText(currentMovie.getSynopsis());

        Picasso.with(this)
                .load(Uri.parse(currentMovie.getPosterURL()))
                .fit()
                .centerInside()
                .into(mPosterImageView);

    }

}
