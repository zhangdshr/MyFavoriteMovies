package com.duosi.myfavoritemovies.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.duosi.myfavoritemovies.R;
import com.duosi.myfavoritemovies.models.Movie;
import com.duosi.myfavoritemovies.repositories.MovieRepository;

import java.util.List;

public class FavMovieRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "FavMovieRecyclerAdapter";

    private List<Movie> movieList;

    public List<Movie> getMovieList() {
        return movieList;
    }

    private OnMovieListener onMovieListener;

    private Context context;

    public void setData(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public FavMovieRecyclerAdapter(OnMovieListener onMovieListener, Context context) {
        this.onMovieListener = onMovieListener;
        this.context = context;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, year;
        AppCompatImageView image;
        OnMovieListener onMovieListener;

        Button btnDelFav;

        public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
            super(itemView);

            this.onMovieListener = onMovieListener;

            title = itemView.findViewById(R.id.movie_title);
            year = itemView.findViewById(R.id.movie_year);
            image = itemView.findViewById(R.id.movie_image);

            btnDelFav = itemView.findViewById(R.id.btn_del_fav);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMovieListener.onMovieClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_fav_movie_list_item, parent, false);
        return new FavMovieRecyclerAdapter.MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(movieList.get(position).getPoster())
                .into(((FavMovieRecyclerAdapter.MovieViewHolder) holder).image);

        ((FavMovieRecyclerAdapter.MovieViewHolder) holder).title.setText(movieList.get(position).getTitle());
        ((FavMovieRecyclerAdapter.MovieViewHolder) holder).year.setText(movieList.get(position).getYear());

        ((FavMovieRecyclerAdapter.MovieViewHolder) holder).btnDelFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: " + position);
                MovieRepository.getInstance(context).delMovieTask(movieList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        }
        return 0;
    }
}
