package com.example.android.popmovie;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();


    /**
     * Create a constructor for our MovieAdapter to inflate the layout file and
     * and list od data we are populating in our gridView.
     * @param context
     * @param movie
     */
    public MovieAdapter(Activity context, List<Movie> movie ) {
        super(context,0, movie);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Get the position of an item in the arrayList at its appropriate position
        Movie currentMovie = getItem(position);

        //Check if the current view is being used, if not then inflate the View
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        //assigning data to their appropriate view
        ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_image);
        Picasso.get()
                .load(currentMovie.getmMovieImage())
                .into(imageView);

        return convertView;
    }
}
