package com.example.android.popmovie;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.popmovie.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler{

    //Initializing
    private String imageText;
    private String titleText;
    private String overViewText;
    private String ratingText;
    private String dateText;
    private String id;

    private ImageView mMovieImage;
    private TextView title;
    private TextView overview;
    private TextView rating;
    private TextView date;
    private TextView mErrorTrailer;
    private TextView mErrorReview;

    private ArrayList<Trailers> trailerArray = new ArrayList<>();
    private ArrayList<Reviews> reviews = new ArrayList<>();

    private LinearLayoutManager mTrailerList;
    private TrailerAdapter mTrailerAdapter;
    private RecyclerView mTrailersRecyclerView;

    private LinearLayoutManager mReviewsList;
    private ReviewsAdapter mReviewsAdapter;
    private RecyclerView mReviewsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Bind view to their data
        mMovieImage = (ImageView) findViewById(R.id.Thumbnail);
        title = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview);
        rating = (TextView) findViewById(R.id.rating);
        date = (TextView) findViewById(R.id.realease_date);

        mTrailersRecyclerView = (RecyclerView) findViewById(R.id.trailer_recycle);
        mErrorTrailer = (TextView) findViewById(R.id.no_trailer);

        mReviewsRecyclerView = (RecyclerView) findViewById(R.id.review_recycle);
        mErrorReview = (TextView) findViewById(R.id.no_review);

        Intent intent = getIntent();

        //Getting values parsed by the main activity
        //And setting them to their appropriate Views
        if (intent != null){
            if (intent.hasExtra("image")){
                imageText = intent.getStringExtra("image");
                Picasso.get().load(imageText).into(mMovieImage);

                //Setting title to its view
                titleText = intent.getStringExtra("title");
                title.setText(titleText);

                //setting overview to its view
                overViewText = intent.getStringExtra("overview");
                overview.setText(overViewText);

                //setting rate to its view
                ratingText = intent.getStringExtra("rating");
                rating.setText(ratingText);

                //Setting date to its view
                dateText = intent.getStringExtra("date");
                date.setText(dateText);

                //Just getting the Id to get Json Data
                id = intent.getStringExtra("id");
            }
        }

        //Creating a linear manager to the Trailers
        //Creating a Recycle and setting it to the Linear manager
        //Setting it to its Adapter
        mTrailerList = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTrailersRecyclerView.setLayoutManager(mTrailerList);
        mTrailersRecyclerView.setHasFixedSize(true);
        mTrailerAdapter = new TrailerAdapter(getApplicationContext(), trailerArray, this);
        mTrailersRecyclerView.setAdapter(mTrailerAdapter);

        new FetchTrailerTask().execute();

        //Creating a linear manager to the Reviews
        //Creating a Recycle and setting it to the Linear manager
        //Setting it to its Adapter
        mReviewsList = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewsRecyclerView.setLayoutManager(mReviewsList);
        mReviewsRecyclerView.setHasFixedSize(true);
        mReviewsAdapter = new ReviewsAdapter(getApplicationContext(), reviews);

        mReviewsRecyclerView.setAdapter(mReviewsAdapter);

        new FetchReviewsTask().execute();

    }

    @Override
    public void onClick(String key) {
        Context context = this;

        playYoutubeVideo(context, key);
    }

    public static void playYoutubeVideo(Context context, String key){
        Intent youtubeAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("Vnd.youtube:" + key));
        Intent youtubeWebIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            context.startActivity(youtubeAppIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(youtubeWebIntent);
        }

    }

    public class FetchTrailerTask extends AsyncTask<String, Void, ArrayList<Trailers>>{

        @Override
        protected ArrayList<Trailers> doInBackground(String... params) {

            trailerArray = null;

            //Getting data to create the youtube url
            trailerArray = NetworkUtils.fetchTrailerData(id);
            return trailerArray;

        }

        @Override
        protected void onPostExecute(ArrayList<Trailers> trailers) {
            if (trailers != null && !trailers.isEmpty()){
                mTrailerAdapter.setmTrailerData(trailers);
            } else {
                mErrorTrailer.setVisibility(View.VISIBLE);
            }

        }
    }

    public class FetchReviewsTask extends AsyncTask<String, Void, ArrayList<Reviews>>{

        @Override
        protected ArrayList<Reviews> doInBackground(String... strings) {
            reviews = null;

            reviews = NetworkUtils.fetchReviewsData(id);
            return  reviews;
        }

        @Override
        protected void onPostExecute(ArrayList<Reviews> reviews) {
            if (reviews != null && !reviews.isEmpty()){
                mReviewsAdapter.setReviewsData(reviews);
            } else {
                mErrorReview.setVisibility(View.VISIBLE);
            }
        }
    }
}
