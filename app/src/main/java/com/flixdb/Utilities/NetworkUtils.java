package com.flixdb.Utilities;

import android.net.Uri;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    // API key for tmdb.
    private static final String APT_KEY = "";

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String POPULARITY_PARAM = "popular";
    private static final String RATING_PARAM = "top_rated";
    private static final String API_KEY_PARAM = "api_key";
    private static String selectedSortType;

    public enum SortType {
        POPULARITY, RATING
    }

    public static URL buildURL(SortType sortType) {

        switch (sortType) {
            case POPULARITY:
                selectedSortType = POPULARITY_PARAM;
                break;
            case RATING:
                selectedSortType = RATING_PARAM;
        }
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(selectedSortType)
                .appendQueryParameter(API_KEY_PARAM, APT_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String fetchJsonFromHttpUrl(URL url) throws IOException {
        String jsonResponse = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            Scanner scanner = new Scanner(urlConnection.getInputStream());
            scanner.useDelimiter("\\A");
            jsonResponse = scanner.next();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return jsonResponse;

    }


}
