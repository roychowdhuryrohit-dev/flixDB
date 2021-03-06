package com.flixdb.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flixdb.Data.Movie;
import com.flixdb.HomeActivity;
import com.flixdb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> mMovieArrayList;
    private Context mContext;
    private MovieAdapterOnClickHandler mClickHandler;

    public MovieAdapter(HomeActivity homeActivity) {
        mContext = homeActivity.getApplicationContext();
        mClickHandler = homeActivity;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView posterImageView;

        private MovieViewHolder(View view) {
            super(view);
            posterImageView = view.findViewById(R.id.movie_poster_iv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovieArrayList.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.poster_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForGridItem, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMovieArrayList.get(position);
        String posterUrl = movie.getPosterURL();
        Picasso.with(mContext)
                .load(Uri.parse(posterUrl))
                .fit()
                .centerInside()
                .into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        if (mMovieArrayList == null) return 0;
        return mMovieArrayList.size();
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
        mMovieArrayList = movieArrayList;
        notifyDataSetChanged();
    }
}
