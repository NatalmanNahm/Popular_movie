package com.example.android.popmovie;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmovie.Adapters.ReviewsAdapter;
import com.example.android.popmovie.Adapters.TrailerAdapter;
import com.example.android.popmovie.Database.AppDatabase;
import com.example.android.popmovie.Database.AppExecutors;
import com.example.android.popmovie.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler{

    //Initializing
    private String imageText;
    private String titleText;
    private String overViewText;
    private String ratingText;
    private String dateText;
    private String id;
    private boolean isFavMovie;
    private String buttonText;

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
    private Button mAddFavMovie;

    private AppDatabase mDb;

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
        mAddFavMovie = (Button) findViewById(R.id.add_fav_movie);


        mTrailersRecyclerView = (RecyclerView) findViewById(R.id.trailer_recycle);
        mErrorTrailer = (TextView) findViewById(R.id.no_trailer);

        mReviewsRecyclerView = (RecyclerView) findViewById(R.id.review_recycle);
        mErrorReview = (TextView) findViewById(R.id.no_review);

        //Initialize member variable for the data base
        mDb = AppDatabase.getInstance(getApplicationContext());

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

                //Getting the statement of the movie
                isFavMovie = intent.getBooleanExtra("isFavorite", false);

                //Getting Button Text
                buttonText = intent.getStringExtra("buttonText");
                mAddFavMovie.setText(buttonText);

                //check if movie is in the database
                //If yes Set the button to delete Favorite
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        String savedId = mDb.favoriteMovieDao().idSaved(id);

                        if (id.equalsIgnoreCase(savedId)){
                            mAddFavMovie.setText(R.string.delete_favorite);
                        }else {
                            mAddFavMovie.setText(buttonText);
                        }
                    }
                });
            }
        }

        //Adding Movie data to the database when the button is clicked
        mAddFavMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //If not a favMovie add movie
                if (!isFavMovie ){
                    buttonText = getString(R.string.delete_favorite);
                    mAddFavMovie.setText(buttonText);
                    isFavMovie = true;

                    final Movie  favMovie =
                            new Movie(id, titleText, imageText, overViewText, ratingText, dateText, isFavMovie, buttonText);

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            //Actually adding the favorite data we got to the database
                            Movie savedMovie = mDb.favoriteMovieDao().toBeDelete(id);
                            String savedId = mDb.favoriteMovieDao().idSaved(id);

                            //if there is no saved Movie in the database, Add one
                            if (savedId == null){
                                mDb.favoriteMovieDao().addFavMov(favMovie);

                            //if there is then delete
                            } else if (savedId.equalsIgnoreCase(id)){
                                mDb.favoriteMovieDao().deleteFavMovie(savedMovie);
                                finish();
                            //if none is valid then add the movie
                            } else {
                                mDb.favoriteMovieDao().addFavMov(favMovie);
                            }
                        }
                    });

                //Delete movie from my Favorite
                } else {
                    buttonText = getString(R.string.mark_as_favorite);
                    mAddFavMovie.setText(buttonText);
                    isFavMovie = true;
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Movie movie = mDb.favoriteMovieDao().toBeDelete(id);
                            mDb.favoriteMovieDao().deleteFavMovie(movie);
                            finish();
                        }
                    });
                }
            }
        });

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
        //Play youtube Video
        playYoutubeVideo(context, key);
    }

    /**
     * this is to open the Youtube app and play the trailer using the the key
     * @param context
     * @param key
     */
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

    //Asynctask for trailer
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

    //Asynctask for review
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
