package com.example.android.popmovie.utilities;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;

import com.example.android.popmovie.BuildConfig;
import com.example.android.popmovie.MainActivity;
import com.example.android.popmovie.Movie;
import com.example.android.popmovie.Reviews;
import com.example.android.popmovie.Trailers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the Movie data servers.
 */

public class NetworkUtils{

    private static final String TAG = NetworkUtils.class.getSimpleName();

    //base URL to be used to get the movie data
    private static final String POP_MOVIE_URL = "https://api.themoviedb.org/3/movie";

    //Parameter to be used to query data from the movie api
    //Will be append onto the base URL
    private static String VIDEOS = "videos";
    private static String REVIEWS = "reviews";
    private static String POP = "popular";
    private static String MOST_RATED = "top_rated";
    private static String API_KEY = "api_key";
    private static String KEY = BuildConfig.myMovieDbApiKey;


    /**
     * Build Url to use to talk to the movie database. This one is to get
     * the most popular movie
     *
     * @return the url to use to query the movie database
     */
    public static URL buildUrl_mostPopular_movie (){

        //Build the URL with the query parameter
        Uri uriBuilder = Uri.parse(POP_MOVIE_URL).buildUpon()
                .appendPath(POP)
                .appendQueryParameter(API_KEY, KEY)
                .build();

        URL url = null;

        try {
            url = new URL(uriBuilder.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    /**
     * Build Url to use to talk to the movie database.
     * this is for the most rated movie
     *
     * @return
     */
    public static URL buildUrl_mostRated_movie (){

        //Build the URL with the query parameter
        Uri uriBuilder = Uri.parse(POP_MOVIE_URL).buildUpon()
                .appendPath(MOST_RATED)
                .appendQueryParameter(API_KEY, KEY)
                .build();

        URL url = null;

        try {
            url = new URL(uriBuilder.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    /**
     * Using id to get trailer key
     * to build the youtube link.
     * @param id
     * @return
     */
    public static URL build_trailer (String id){
        Uri uri = Uri.parse(POP_MOVIE_URL).buildUpon()
                .appendPath(id)
                .appendPath(VIDEOS)
                .appendQueryParameter(API_KEY, KEY)
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());

        } catch (MalformedURLException e) {
            Log.v(TAG, "Built URI" + url);
        }
        return url;
    }

    /**
     * Building the Url to request the review data
     * @param id
     * @return
     */
    public static URL build_review (String id){
        Uri uri = Uri.parse(POP_MOVIE_URL).buildUpon()
                .appendPath(id)
                .appendPath(REVIEWS)
                .appendQueryParameter(API_KEY, KEY)
                .build();
        URL url = null;

        try {
            url = new URL(uri.toString());
        }catch (MalformedURLException e) {
            Log.v(TAG, "Built URI" + url);
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {

        String jsonResponse = "";

        //if the url is null the return early
        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        //Trying to get connection the server and if the status is 200
        //That means the connection was a success and then we can retrieve data needed
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /*milliseconds*/);
            urlConnection.setConnectTimeout(15000 /*milliseconds*/);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "MakeHTTPRequest: Problem retrieving data from JSON result ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream (InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Query the Movie database and return a list of {@link Movie} objects.
     * for most popular Movies
     */
    public static ArrayList<Movie> fetchPopMovieData () throws MalformedURLException {

        //Create a Url Object
        URL url = buildUrl_mostPopular_movie();

        //Perform HTTP request to the url and return JSON response back
        String jsonREsponse = null;

        try {
            jsonREsponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request.", e);
        }

        //Extract data needed from the json response
        ArrayList<Movie> movies = OpenJsonUtils.extractFeatureFromJson(jsonREsponse);


        return movies;
    }

    /**
     * Query the Movie database and return a list of {@link Movie} objects.
     * for most rated Movies
     */
    public static ArrayList<Movie> fetchRatedMovieData (){
        //create a Url Object
        URL url = buildUrl_mostRated_movie();

        //Perform HTTP request to the url and return JSON response back
        String jsonREsponse = null;

        try {
            jsonREsponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request.", e);
        }

        //Extract data needed from the json response
        ArrayList<Movie> movies = OpenJsonUtils.extractFeatureFromJson(jsonREsponse);

        return movies;
    }

    /**
     * Query the Trailer data and then return a list of Trailer data
     * @param id
     * @return
     */
    public static ArrayList<Trailers> fetchTrailerData(String id){
        //create a url Object
        URL url = build_trailer(id);

        String jsonRespnse = null;

        try {
            jsonRespnse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request", e);
        }


        ArrayList<Trailers> arrayList = OpenJsonUtils.jsonTrailerOpener(jsonRespnse);

        return arrayList;
    }

    /**
     * Query the Review data and then return a list of Review object
     * @param id
     * @return
     */
    public static ArrayList <Reviews> fetchReviewsData (String id){

        URL url = build_review(id);

        String jsonReponse = null;

        try {
            jsonReponse = getResponseFromHttpUrl(url);

        }catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request", e);
        }

        ArrayList<Reviews> reviews = OpenJsonUtils.jsonReviewsOpener(jsonReponse);

        return reviews;
    }
}
