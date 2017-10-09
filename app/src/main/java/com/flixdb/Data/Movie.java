package com.flixdb.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private final String mTitle;
    private final String mReleaseDate;
    private final String mPosterUrl;
    private final String mSynopsis;
    private final String mVoteAverage;

    public Movie(String title, String releaseDate, String posterURL, String synopsis, String voteAverage) {
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterUrl = posterURL;
        mSynopsis = synopsis;
        mVoteAverage = voteAverage;
    }

    private Movie(Parcel parcel) {
        mTitle = parcel.readString();
        mReleaseDate = parcel.readString();
        mPosterUrl = parcel.readString();
        mSynopsis = parcel.readString();
        mVoteAverage = parcel.readString();
    }

    public String getPosterURL() {
        return mPosterUrl;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mPosterUrl);
        parcel.writeString(mSynopsis);
        parcel.writeString(mVoteAverage);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[0];
        }
    };


}
