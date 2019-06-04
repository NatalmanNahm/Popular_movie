package com.example.android.popmovie;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.popmovie.utilities.NetworkUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads Movie Items by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class MovieLoader extends AsyncTaskLoader <ArrayList<Movie>> {

    /**
     * Create a constructor for the loader
     * @param context
     */
    public MovieLoader (Context context){
        super(context);
    }

    @Override
    protected void onStartLoading (){
        forceLoad();
    }

    /**
     * background thread where we do the network request
     * @return a list of Movie object
     */
    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {

        // Perform the network request, parse the response, and extract a list of Movie objects.
        ArrayList<Movie> movies = null;
        try {
            movies = NetworkUtils.fetchPopMovieData();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
