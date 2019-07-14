package com.example.android.popmovie;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popmovie.Database.AppDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    //Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    //Initializing the Live data for the movie
    LiveData<List<Movie>> movie;

    public MainViewModel(@NonNull Application application) {
        super(application);
        //Using the laodAllLiveFavMovies to initialize the Movie Variable
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the Movies from the DataBase");
        movie = appDatabase.favoriteMovieDao().laodAllLiveFavMovies();
    }

    //Creating a getter and a setter

    public LiveData<List<Movie>> getMovie() {
        return movie;
    }

}
