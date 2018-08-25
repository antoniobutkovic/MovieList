package com.example.toni.movielist.ui.search.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.listener.MovieClickListener;
import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.ui.main.adapter.MovieRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder>{

    private MovieClickListener listener;
    private List<Movie> mMovies;

    public SearchRecyclerAdapter(MovieClickListener listener){
        this.listener = listener;
        mMovies = new ArrayList<>();
    }

    public void updateMovies(List<Movie> movies){
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public void addMoreMovies(List<Movie> movies){
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchRecyclerAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_searched_movie, parent, false);
        return new SearchRecyclerAdapter.SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapter.SearchViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        String posterUrl = Constants.IMAGES_BASE_URL + movie.getPosterPath();

        Glide.with(holder.context).load(posterUrl).into(holder.movieIv);
        holder.titleTv.setText(movie.getTitle());

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        Context context;

        @BindView(R.id.movie_search_card_imageview)
        ImageView movieIv;

        @BindView(R.id.movie_search_card_title_textview)
        TextView titleTv;

        public SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        @OnClick
        public void onMovieClick(){
            listener.onMovieClicked(mMovies.get(getAdapterPosition()).getId());
        }
    }

}
