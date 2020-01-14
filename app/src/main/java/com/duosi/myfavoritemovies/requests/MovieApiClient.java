package com.duosi.myfavoritemovies.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.duosi.myfavoritemovies.MyExecutors;
import com.duosi.myfavoritemovies.models.Movie;
import com.duosi.myfavoritemovies.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.duosi.myfavoritemovies.utils.Constants.NETWORK_TIMEOUT;

public class MovieApiClient {

    private static final String TAG = "MovieApiClient";

    private static MovieApiClient instance;

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MutableLiveData<List<Movie>> mMovies;

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    public void searchMoviesApi(String s) {

        Log.i(TAG, "searchMoviesApi: " + s);

        if(retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }
        retrieveMoviesRunnable = new RetrieveMoviesRunnable(s);
        final Future handler = MyExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        MyExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private class RetrieveMoviesRunnable implements Runnable {

        private String searchContent;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String s) {
            this.searchContent = s;
            cancelRequest = false;
        }

        @Override
        public void run() {

            Log.i(TAG, "run: " + searchContent);

            try {
                Response response = getMovies(searchContent).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<Movie> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    mMovies.postValue(list);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

        }

        private Call<MovieSearchResponse> getMovies(String s) {
            return RetrofitService.getMovieApi().searchMovies(s, Constants.RESPONSE_TYPE, Constants.RESPONSE_R, Constants.API_KEY);
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the search request.");
            cancelRequest = true;
        }
    }

    public void cancelRequest(){
        if(retrieveMoviesRunnable != null){
            retrieveMoviesRunnable.cancelRequest();
        }
    }

}
