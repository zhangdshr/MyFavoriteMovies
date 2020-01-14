package com.duosi.myfavoritemovies.requests;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    // SEARCH
    @GET("/")
    Call<MovieSearchResponse> searchMovies(
            @Query("s") String s,
            @Query("type") String type,
            @Query("r") String r,
            @Query("apikey") String apikey
    );

}
