package com.example.android.popmovie;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.example.android.popmovie.utilities.NetworkUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, GridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter();

        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData();
    }


    private void loadMovieData() {
        showWeatherDataView();

        new FetchMovieTask().execute();
    }

    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
//        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, List<String>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(String... params)
        {
            List<String> movies = null;
            try {
                movies = NetworkUtils.fetchPopMovieData();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return movies;
        }

        @Override
        protected void onPostExecute(List<String> movies) {
            if (movies!= null && !movies.isEmpty()) {
                mMovieAdapter.setmMoviedata(movies);
            }
        }
    }
}
