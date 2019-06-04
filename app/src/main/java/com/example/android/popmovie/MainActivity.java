package com.example.android.popmovie;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

    /**
     * Constant value for the Movie loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int MOVIE_LOADER_ID = 1;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieAdapter = new MovieAdapter(this, new ArrayList<Movie>());

        // Get a reference to the ListView, and attach this adapter to it.
        GridView gridView = (GridView) findViewById(R.id.movie_grid);
        gridView.setAdapter(mMovieAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()){
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
        }else {

        }
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        return new Loader<ArrayList<Movie>>(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }
}
