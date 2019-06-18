package com.example.android.popmovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popmovie.utilities.NetworkUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    //Initializing
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ArrayList<Movie> mMovie = new ArrayList<>();
    private TextView mErrorMessage;
    private ProgressBar mLoading;

    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting reference of the recycleView so we can set adapter it
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle);
        //used to display the the error message and hide it when needed
        mErrorMessage = (TextView) findViewById(R.id.error_message);

        //Creating a gridview where we can add all the movie item we get from the movie data
        //or Get back to where we left by saving a reference to the gridView
        gridLayoutManager =
                new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);

        //set the gridview to the recycleView
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        //create adapter
        mMovieAdapter = new MovieAdapter(getApplicationContext(), mMovie,this);

        //set Adapter
        mRecyclerView.setAdapter(mMovieAdapter);

        //creating a progress bar that let the user know that there data is been loaded
        mLoading = (ProgressBar) findViewById(R.id.loading_circle);

        if (savedInstanceState == null || !savedInstanceState.containsKey("movie")){
            loadMovieData();
        }else {
            mMovie = savedInstanceState.getParcelableArrayList("movie");
            loadMovieData();
        }


    }

    /**
     * helper method to Load data we get from movie data
     */
    private void loadMovieData() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortBy = sharedPrefs.getString("sort_by",
                "@string/settings_sort_by_most_popular_label");

        showMovieDataView();

        new FetchMovieTask().execute(sortBy);
    }

    /**
     * helper method to hide the error message
     * and show movie data when it is available
     */
    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessage.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Helper method to hide show movie error message
     * and hide recycle view when movie data is not available
     */
    private void showErrorMessage(){
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessage.setVisibility(View.VISIBLE);
    }
    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params)
        {
            mMovie = null;
            String sorted = params[0];
            try {
                //Populating the UI based on the choice of the user "Most Popular"
                //or "Most Rated".
                if (sorted.equalsIgnoreCase("most popular")){
                    mMovie = NetworkUtils.fetchPopMovieData();
                }else {
                    mMovie = NetworkUtils.fetchRatedMovieData();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return mMovie;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            //Hiding the progress bar
            mLoading.setVisibility(View.INVISIBLE);
            if (movies!= null && !movies.isEmpty()) {
                //Displaying the movie image to the user
                showMovieDataView();
                mMovieAdapter.setmMoviedata(movies);
            }else {
                showErrorMessage();
            }
        }
    }
    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent settingsIntent = new Intent(this, SettingMenu.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(String image, String title, String overview, String rating, String date ){
        Context context = this;
        Class destinationClass = MovieDetails.class;
        //Creating Intent
        Intent intent = new Intent(context, destinationClass);
        //Parsing the image, title, overview, rating, and date variable
        //detail activity
        intent.putExtra("image", image);
        intent.putExtra("title", title);
        intent.putExtra("overview", overview);
        intent.putExtra("rating", rating);
        intent.putExtra("date", date);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movie", mMovie);
        super.onSaveInstanceState(outState);
    }
}
