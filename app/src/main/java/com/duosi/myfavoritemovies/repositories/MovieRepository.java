package com.duosi.myfavoritemovies.repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.duosi.myfavoritemovies.models.Movie;
import com.duosi.myfavoritemovies.persistence.MovieDao;
import com.duosi.myfavoritemovies.persistence.MovieDatabase;
import com.duosi.myfavoritemovies.requests.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static final String TAG = "MovieRepository";

    private MovieDatabase movieDatabase;

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private MovieDao movieDao;

    public static MovieRepository getInstance(Context context) {
        if (instance == null) {
            instance = new MovieRepository(context);
        }
        return instance;
    }

    private MovieRepository(Context context) {
        movieApiClient = MovieApiClient.getInstance();
        movieDatabase = MovieDatabase.getInstance(context);
        movieDao = movieDatabase.getMovieDao();
    }

    public LiveData<List<Movie>> getMovies() {
        return movieApiClient.getMovies();
    }

    public void searchMoviesApi(String s) {
        movieApiClient.searchMoviesApi(s);
    }

    public void cancelRequest() {
        movieApiClient.cancelRequest();
    }

    public void insertMovieTask(Movie movie) {
        new InsertAsyncTask(movieDao).execute(movie);
    }

    public void delMovieTask(Movie movie) {
        new DelAsyncTask(movieDao).execute(movie);
    }

    public LiveData<List<Movie>> getMoviesTaskFromDB() {
        return movieDao.getMovies();
    }

    private static class InsertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao;

        private InsertAsyncTask(MovieDao dao) {
            movieDao = dao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            Movie movieAdd = movies[0];
            Movie movie = new Movie(movieAdd.getTitle(), movieAdd.getYear(), movieAdd.getImdbID(), movieAdd.getType(), movieAdd.getPoster());
            movieDao.insertMovie(movie);
            return null;
        }
    }

    private static class DelAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao;

        private DelAsyncTask(MovieDao dao) {
            movieDao = dao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.deleteByMovieTitle(movies[0].getTitle());
            return null;
        }
    }

}
