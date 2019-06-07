package com.example.android.popmovie.utilities;


import android.net.Uri;
import android.util.Log;

import com.example.android.popmovie.Movie;

import java.io.BufferedReader;
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

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String POP_MOVIE_URL = "http://api.themoviedb.org/3/discover/movie";

    private static String SORT_BY = "sort_by";
    private static String POP = "popularity.desc";
    private static String MOST_RATED = "vote_average.desc";
    private static String API_KEY = "api_key";
    private static String KEY = "4c847680f6bc9cd56eff4d157bedc568";

    //private static String URLMOV = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=4c847680f6bc9cd56eff4d157bedc568";


    /**
     * Build Url to use to talk to the movie database. This one is to get
     * the most popular movie
     *
     * @return the url to use to query the movie database
     */
    public static URL buildUrl_mostPopular_movie (){
        Uri uriBuilder = Uri.parse(POP_MOVIE_URL).buildUpon()
                .appendQueryParameter(SORT_BY, POP)
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
        Uri uriBuilder = Uri.parse(POP_MOVIE_URL).buildUpon()
                .appendQueryParameter(SORT_BY, MOST_RATED)
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
    public static List<String> fetchPopMovieData () throws MalformedURLException {

        //URL urlK = new URL(URLMOV);

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
        List<String> movies = OpenJsonUtils.extractFeatureFromJson(jsonREsponse);


        return movies;
    }

    /**
     * Query the Movie database and return a list of {@link Movie} objects.
     * for most rated Movies
     */
    public static List<String> fetchRatedMovieData (){
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
        List<String> movies = OpenJsonUtils.extractFeatureFromJson(jsonREsponse);

        return movies;
    }
}
