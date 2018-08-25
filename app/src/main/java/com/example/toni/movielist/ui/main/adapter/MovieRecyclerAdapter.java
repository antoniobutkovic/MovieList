package com.example.toni.movielist.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.listener.MovieClickListener;
import com.example.toni.movielist.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> implements Filterable{

    private MovieClickListener listener;
    private List<Movie> mMovies;
    private List<Movie> mMoviesFiltered;

    public MovieRecyclerAdapter(MovieClickListener listener){
        this.listener = listener;
        mMovies = new ArrayList<>();
        mMoviesFiltered = new ArrayList<>();
    }

    public void updateMovies(List<Movie> movies){
        mMovies.clear();
        mMovies.addAll(movies);
        mMoviesFiltered = mMovies;
        notifyDataSetChanged();
    }

    public void addMoreMovies(List<Movie> movies){
        mMovies.addAll(movies);
        mMoviesFiltered = mMovies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = mMoviesFiltered.get(position);

        String posterUrl = Constants.IMAGES_BASE_URL + movie.getPosterPath();

        Glide.with(holder.context).load(posterUrl).into(holder.movieIv);
        holder.titleTv.setText(movie.getTitle());

    }

    @Override
    public int getItemCount() {
        return mMoviesFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    mMoviesFiltered = mMovies;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie row : mMovies) {
                        if (row.getTitle().toLowerCase().contains(charString)) {
                            filteredList.add(row);
                        }
                    }
                    mMoviesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mMoviesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mMoviesFiltered = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {

        Context context;

        @BindView(R.id.movie_card_imageview)
        ImageView movieIv;

        @BindView(R.id.movie_card_title_textview)
        TextView titleTv;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        @OnClick
        public void onMovieClick(){
            listener.onMovieClicked(mMoviesFiltered.get(getAdapterPosition()).getId());
        }
    }
}
