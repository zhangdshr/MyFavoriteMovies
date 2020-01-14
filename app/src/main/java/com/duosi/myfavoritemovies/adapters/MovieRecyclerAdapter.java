package com.duosi.myfavoritemovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.duosi.myfavoritemovies.R;
import com.duosi.myfavoritemovies.models.Movie;
import com.duosi.myfavoritemovies.repositories.MovieRepository;

import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> movieList;

    public List<Movie> getMovieList() {
        return movieList;
    }

    private OnMovieListener onMovieListener;

    private Context context;

    public void setData(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public MovieRecyclerAdapter(OnMovieListener onMovieListener, Context context) {
        this.onMovieListener = onMovieListener;
        this.context = context;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, year;
        AppCompatImageView image;
        OnMovieListener onMovieListener;

        ImageView imageAddFav;

        public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
            super(itemView);

            this.onMovieListener = onMovieListener;

            title = itemView.findViewById(R.id.movie_title);
            year = itemView.findViewById(R.id.movie_year);
            image = itemView.findViewById(R.id.movie_image);

            imageAddFav = itemView.findViewById(R.id.image_add_fav);

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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_list_item, parent, false);
        return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(movieList.get(position).getPoster())
                .into(((MovieViewHolder) holder).image);

        ((MovieViewHolder) holder).title.setText(movieList.get(position).getTitle());
        ((MovieViewHolder) holder).year.setText(movieList.get(position).getYear());

        ((MovieViewHolder) holder).imageAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieRepository.getInstance(context).insertMovieTask(movieList.get(position));
                Toast.makeText(context, "Added to fav list Successfully", Toast.LENGTH_SHORT).show();
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
