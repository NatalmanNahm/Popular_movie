package com.example.android.popmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    //Initializing
    private String imageText;
    private String titleText;
    private String overViewText;
    private String ratingText;
    private String dateText;

    private ImageView mMovieImage;
    private TextView title;
    private TextView overview;
    private TextView rating;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mMovieImage = (ImageView) findViewById(R.id.Thumbnail);
        title = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview);
        rating = (TextView) findViewById(R.id.rating);
        date = (TextView) findViewById(R.id.realease_date);


        Intent intent = getIntent();

        //Getting values parsed by the main activity
        //And setting them to their appropriate Views
        if (intent != null){
            if (intent.hasExtra("image")){
                imageText = intent.getStringExtra("image");
                Picasso.get().load(imageText).into(mMovieImage);

                titleText = intent.getStringExtra("title");
                title.setText(titleText);

                overViewText = intent.getStringExtra("overview");
                overview.setText(overViewText);

                ratingText = intent.getStringExtra("rating");
                rating.setText(ratingText);

                dateText = intent.getStringExtra("date");
                date.setText(dateText);
            }
        }
    }
}
