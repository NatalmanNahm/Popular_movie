package com.example.android.popmovie.utilities;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.android.popmovie.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Open Json that is given by the Movie data
 */
public class OpenJsonUtils {

    private static final String TAG = OpenJsonUtils.class.getSimpleName();
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";



    /**
     * Create a private constructor, so no one could access it
     */
    private OpenJsonUtils(){

    }

    public static ArrayList<Movie> extractFeatureFromJson(String json){

        //If the string Json is empty the return early
        if (TextUtils.isEmpty(json)){
            return null;
        }

        //create empty ArrayList of that will hold our data of movie needed
        ArrayList<Movie> arrayList = new ArrayList<>();

        //Try to parse the Json and if the is a problem the JSONException will be Thrown.
        //It will be then catch in the catch block, so the app doesn't crash
        try {
            //Build movie data with the data correspondent to what we need
            JSONObject rootJson = new JSONObject(json);

            //Get down to the result where we can get our data
            JSONArray results = rootJson.getJSONArray("results");

            //Iterate through the array to get the data we want
            for (int i = 0; i<results.length(); i++){
                JSONObject jsonObject = results.getJSONObject(i);

                String title = jsonObject.getString("title");
                String overview = jsonObject.getString("overview");
                String rating = jsonObject.getString("vote_average");
                String dateRelease = jsonObject.getString("release_date");
                String image = jsonObject.getString("poster_path");

                String imageConstruct = IMAGE_URL + IMAGE_SIZE + image;



                arrayList.add(new Movie(title, overview, rating, dateRelease, imageConstruct));

            }

        } catch (JSONException e) {
            //If there is a problem parsing the Json object print this message
            Log.e(TAG, "Error parsing the Movie Json object");
        }
        //return list of Movie data
        return arrayList;
    }


}
