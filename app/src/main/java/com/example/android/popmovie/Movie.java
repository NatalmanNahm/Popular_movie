package com.example.android.popmovie;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "FavoriteMovie")
public class Movie implements Parcelable {

    /** Initializing data to be used in the constructor */
    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String image;
    private String overview;
    private String rating;
    private String dateRelease;
    private boolean isFav;
    private String buttonText;
    //For drawable reference id for image


    /**
     * Creating a Movie Constructor with only one param (Movie)
     * @param image
     */
    public Movie(String image){
        this.image = image;
    }


    /**
     * Creating a constructor for our movie class
     *
     * @param titleMovie
     * @param overviewMovie
     * @param ratingMovie
     * @param dateReleaseMovie
     * @param imageMovie
     * @param idMovie
     * @param fav
     * @param text
     */

    public Movie(@NonNull String idMovie, String titleMovie, String imageMovie, String overviewMovie,
                 String ratingMovie, String dateReleaseMovie, boolean fav, String text){

        id = idMovie;
        title = titleMovie;
        image = imageMovie;
        overview = overviewMovie;
        rating = ratingMovie;
        dateRelease = dateReleaseMovie;
        isFav = fav;
        buttonText = text;

    }

    //Creating parcel to be read from
    private Movie (Parcel parcel){
        title = parcel.readString();
        overview = parcel.readString();
        rating = parcel.readString();
        dateRelease = parcel.readString();
        image = parcel.readString();
        id = parcel.readString();
        buttonText = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Writing to the parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(rating);
        dest.writeString(dateRelease);
        dest.writeString(image);
        dest.writeString(id);
        dest.writeString(buttonText);
    }

    /**
     * Creating a parcel to be used to in case we need to use it on saveInstant
     */
    @Ignore
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

    /**
     * Creating getters for our movie variables
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(String dateRelease) {
        this.dateRelease = dateRelease;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
}
