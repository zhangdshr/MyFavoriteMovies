package com.duosi.myfavoritemovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.duosi.myfavoritemovies.adapters.MovieRecyclerAdapter;
import com.duosi.myfavoritemovies.adapters.OnMovieListener;
import com.duosi.myfavoritemovies.models.Movie;
import com.duosi.myfavoritemovies.repositories.MovieRepository;
import com.duosi.myfavoritemovies.utils.VerticalSpacingItemDecorator;
import com.duosi.myfavoritemovies.viewmodels.SearchMovieListViewModel;

import java.util.List;

public class FindMoviesActivity extends AppCompatActivity implements OnMovieListener {

    private static final String TAG = "FindMoviesActivity";

    private SearchMovieListViewModel searchMovieListViewModel;

    private RecyclerView mRecyclerView;
    private MovieRecyclerAdapter mAdapter;

    private ImageView back;
    private EditText edit;
    private Button btnFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_movies);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        back = findViewById(R.id.back);
        edit = findViewById(R.id.edit_find);
        btnFind = findViewById(R.id.btn_find);

        mRecyclerView = findViewById(R.id.movie_list);

        searchMovieListViewModel = ViewModelProviders.of(this).get(SearchMovieListViewModel.class);

        initRecyclerView();
        subscribeObservers();

        initView();

    }

    private void initView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovieListViewModel.executeBack();
                finish();
            }
        });
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit.getText() == null) {
                    Toast.makeText(FindMoviesActivity.this, R.string.search_null_notify, Toast.LENGTH_SHORT).show();
                } else {
                    searchMovieListViewModel.searchMoviesApi(edit.getText().toString());
                }
            }
        });

    }

    private void initRecyclerView() {
        mAdapter = new MovieRecyclerAdapter(this, getApplicationContext());
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void subscribeObservers() {
        searchMovieListViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies != null) {
                    mAdapter.setData(movies);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        Log.i(TAG, "onMovieClick: " + position);
        MovieRepository.getInstance(this).insertMovieTask(mAdapter.getMovieList().get(position));
    }
}
