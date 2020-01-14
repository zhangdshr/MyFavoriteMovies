package com.duosi.myfavoritemovies.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.duosi.myfavoritemovies.models.Movie;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {

    @Insert(onConflict = REPLACE)
    void insertMovie(Movie movie);

    @Query("SELECT * FROM movies ORDER BY title ASC")
    LiveData<List<Movie>> getMovies();

    @Query("DELETE FROM movies WHERE title = :title")
    void deleteByMovieTitle(String title);

}
