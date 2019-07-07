package com.example.android.popmovie;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private ArrayList<Movie> mMovie = new ArrayList<>();
    private Context mContext;

    //Create an onClickHandler to make it easier for the
    // activity to interact with the recycleView
    private final MovieAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler{
        void onClick(String image, String title, String overView, String rating, String date, String id);
    }

    /** OnClick Handelr for the adapter
     *
     * @param clickHandler Single handler is called when an item is clicked
     */
    public MovieAdapter (Context context, ArrayList<Movie> movie, MovieAdapterOnClickHandler clickHandler){
        mContext = context;
        mMovie = movie;
        mClickHandler = clickHandler;
    }


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
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.bindMovie(mMovie.get(i));
    }

    @Override
    public int getItemCount() {
        if (null == mMovie) return 0;
        return mMovie.size();
    }

    public void setmMoviedata(ArrayList<Movie> movie){
        mMovie = movie;
        notifyDataSetChanged();
    }

    /**
     * Cache of the children views for a Movie list item.
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        @Bind(R.id.movie_image) ImageView mImage;
        private Context mContext;

        public MovieViewHolder(View view){
            super(view);
            mContext = view.getContext();
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);

        }
        //This is used to populate the thumbnails for our main page
        public void bindMovie(Movie movie){

            Picasso.get().load(movie.getmMovieImage()).into(mImage);
        }

        /** This get called by the child views during a click
         *
         * @param v View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovie.get(adapterPosition);

            String image = movie.getmMovieImage();
            String title = movie.getMtitle();
            String rating = movie.getmRating();
            String overview = movie.getmOverview();
            String dateRealease = movie.getmDateRelease();
            String id = movie.getmMovieId();

            mClickHandler.onClick(image, title, overview, rating, dateRealease, id);
        }
    }
}