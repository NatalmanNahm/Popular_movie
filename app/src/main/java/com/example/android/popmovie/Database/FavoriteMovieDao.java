package com.example.android.popmovie.Database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.popmovie.Movie;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface FavoriteMovieDao {

    //For our live Data
    @Query("SELECT * FROM FavoriteMovie ORDER BY id")
    LiveData<List<Movie>> laodAllLiveFavMovies();

    //TO query data inside the doInBackground
    @Query("SELECT * FROM FavoriteMovie ORDER BY id")
    List<Movie> laodAllFavMovies();

    @Query("SELECT id FROM favoritemovie WHERE id = :id")
    String idSaved(String id);

    @Insert
    void addFavMov(Movie favMovie);

    @Delete()
    void deleteFavMovie (Movie movie);

    @Query("SELECT * FROM FavoriteMovie WHERE id = :id")
    Movie toBeDelete(String id);

}
