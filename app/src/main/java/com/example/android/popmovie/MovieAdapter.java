package com.example.android.popmovie;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private List<String> mMoviedata;
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder movieViewHlder, int i) {
        String currentMovieImage = mMoviedata.get(i);
        Picasso.get().load(currentMovieImage).into(movieViewHlder.mImage);
    }

    @Override
    public int getItemCount() {
        if (null == mMoviedata) return 0;
        return mMoviedata.size();
    }

    /**
     * Cache of the children views for a Movie list item.
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder{

        public final ImageView mImage;

        public MovieViewHolder(View view){
            super(view);
            mImage = (ImageView) view.findViewById(R.id.movie_image);

        }

    }
    public void setmMoviedata(List<String> weatherData){
        mMoviedata = weatherData;
        notifyDataSetChanged();
    }
}