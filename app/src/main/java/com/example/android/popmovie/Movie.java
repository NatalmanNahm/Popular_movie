package com.example.android.popmovie;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    /** Initializing data to be used in the constructor */

    private String mtitle;
    private String mOverview;
    private int mRating;
    private String mDateRelease;
    private int mMovieImage; //For drawable reference id for image


    /**
     * Creating a constructor for our movie class
     *
     * @param title
     * @param overview
     * @param rating
     * @param dateRelease
     * @param image
     *
     */

    public Movie(String title, String overview, int rating, String dateRelease, int image){

        this.mtitle = title;
        this.mOverview = overview;
        this.mRating = rating;
        this.mDateRelease = dateRelease;
        this.mMovieImage = image;

    }

    private Movie (Parcel parcel){
        mtitle = parcel.readString();
        mOverview = parcel.readString();
        mRating = parcel.readInt();
        mDateRelease = parcel.readString();
        mMovieImage = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mtitle);
        dest.writeString(mOverview);
        dest.writeInt(mRating);
        dest.writeString(mDateRelease);
        dest.writeInt(mMovieImage);
    }

    public final Parcelable.Creator<Movie> CREATOR= new Parcelable.Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0];
        }
    };

    public String getMtitle() {
        return mtitle;
    }

    public String getmOverview() {
        return mOverview;
    }

    public int getmRating() {
        return mRating;
    }

    public String getmDateRelease() {
        return mDateRelease;
    }

    public int getmMovieImage() {
        return mMovieImage;
    }
}
