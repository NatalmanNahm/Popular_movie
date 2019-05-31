package com.example.android.popmovie;

public class Movie {

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
