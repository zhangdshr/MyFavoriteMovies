package com.duosi.myfavoritemovies.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.duosi.myfavoritemovies.models.Movie;
import com.duosi.myfavoritemovies.repositories.MovieRepository;

import java.util.List;

public class SearchMovieListViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    public SearchMovieListViewModel(@NonNull Application application) {
        super(application);
        movieRepository = MovieRepository.getInstance(application);
    }

    public LiveData<List<Movie>> getMovies(){
        return movieRepository.getMovies();
    }

    public void searchMoviesApi(String s){
        movieRepository.searchMoviesApi(s);
    }

    public void executeBack(){
        movieRepository.cancelRequest();
    }

}
