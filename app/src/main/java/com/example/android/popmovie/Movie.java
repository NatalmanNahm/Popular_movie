package com.example.android.popmovie;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    /** Initializing data to be used in the constructor */

    private String mtitle;
    private String mOverview;
    private String mRating;
    private String mDateRelease;
    private String mMovieImage; //For drawable reference id for image


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

    public Movie(String image){
        this.mMovieImage = image;
    }

    public Movie(String title, String overview, String rating, String dateRelease, String image){

        this.mtitle = title;
        this.mOverview = overview;
        this.mRating = rating;
        this.mDateRelease = dateRelease;
        this.mMovieImage = image;

    }

    private Movie (Parcel parcel){
        mtitle = parcel.readString();
        mOverview = parcel.readString();
        mRating = parcel.readString();
        mDateRelease = parcel.readString();
        mMovieImage = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mtitle);
        dest.writeString(mOverview);
        dest.writeString(mRating);
        dest.writeString(mDateRelease);
        dest.writeString(mMovieImage);
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

    public String getmRating() {
        return mRating;
    }

    public String getmDateRelease() {
        return mDateRelease;
    }

    public String getmMovieImage() {
        return mMovieImage;
    }
}
