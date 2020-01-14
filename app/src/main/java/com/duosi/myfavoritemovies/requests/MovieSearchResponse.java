package com.duosi.myfavoritemovies.requests;

import com.duosi.myfavoritemovies.models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSearchResponse {

    @SerializedName("Search")
    @Expose()
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                ", movies=" + movies +
                '}';
    }

}
