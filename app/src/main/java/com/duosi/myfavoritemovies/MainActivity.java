package com.duosi.myfavoritemovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.duosi.myfavoritemovies.adapters.FavMovieRecyclerAdapter;
import com.duosi.myfavoritemovies.adapters.OnMovieListener;
import com.duosi.myfavoritemovies.models.Movie;
import com.duosi.myfavoritemovies.utils.VerticalSpacingItemDecorator;
import com.duosi.myfavoritemovies.viewmodels.FavMovieListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMovieListener {

    private static final String TAG = "MainActivity";

    private Button buttonFindMovies;

    private FavMovieListViewModel favMovieListViewModel;

    private RecyclerView mRecyclerView;
    private FavMovieRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mRecyclerView = findViewById(R.id.movie_list);

        favMovieListViewModel = ViewModelProviders.of(this).get(FavMovieListViewModel.class);

        initRecyclerView();

        subscribeObservers();

        initView();
    }

    private void initRecyclerView() {
        mAdapter = new FavMovieRecyclerAdapter(this, getApplicationContext());
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void subscribeObservers() {
        favMovieListViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies != null) {

                    mAdapter.setData(movies);
                    mAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    private void initView() {
        buttonFindMovies = findViewById(R.id.button_find_movies);
        buttonFindMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSearchIntent = new Intent();
                goSearchIntent.setClass(MainActivity.this, FindMoviesActivity.class);
                startActivity(goSearchIntent);
            }
        });
    }

    @Override
    public void onMovieClick(int position) {

    }
}
