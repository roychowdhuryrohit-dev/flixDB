package com.flixdb.Utilities;

import com.flixdb.Data.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieJsonUtils {

    public static ArrayList<Movie> getMovieArrayListFromJson(String movieJson) throws Exception {

        final String RESULTS_PARAM = "results";
        final String OVERVIEW_PARAM = "overview";
        final String TITLE_PARAM = "title";
        final String VOTE_PARAM = "vote_average";
        final String POSTER_PATH_PARAM = "poster_path";
        final String RELEASE_DATE_PARAM = "release_date";
        final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

        JSONObject movieJsonObject = new JSONObject(movieJson);
        JSONArray resultsJsonArray = movieJsonObject.getJSONArray(RESULTS_PARAM);

        ArrayList<Movie> movieArrayList = new ArrayList<>(resultsJsonArray.length());

        for (int pos = 0; pos < resultsJsonArray.length(); pos++) {
            JSONObject jsonObject = resultsJsonArray.getJSONObject(pos);

            String title = jsonObject.getString(TITLE_PARAM);
            String synopsis = jsonObject.getString(OVERVIEW_PARAM);
            String vote = jsonObject.getString(VOTE_PARAM);
            String posterURL = IMAGE_BASE_URL + jsonObject.getString(POSTER_PATH_PARAM);
            String releaseDate = DateUtils.formatDate(jsonObject.getString(RELEASE_DATE_PARAM));

            Movie movie = new Movie(title, releaseDate, posterURL,synopsis,vote);
            movieArrayList.add(pos,movie);

        }
        return movieArrayList;


    }


}
