package com.example.android.popmovie;

public class Trailers {

    /**
     * Initializing the variables to be used
     */
    private String mTrailerKey;
    private String mName;

    /**
     * Trailer constructor for making the youtube video
     * @param key
     * @param name
     */
    public Trailers (String key, String name){
        mTrailerKey = key;
        mName = name;
    }

    /**
     * creating getteres for the trailerKey and name
     * @return
     */
    public String getmTrailerKey(){
        return mTrailerKey;
    }

    public String getmName (){
        return mName;
    }

}
