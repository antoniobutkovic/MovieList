package com.example.toni.movielist.ui.main.toprated;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toni.movielist.App;
import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.listener.MovieClickListener;
import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.model.MovieResponse;
import com.example.toni.movielist.presentation.MoviesPresenter;
import com.example.toni.movielist.ui.main.adapter.MovieRecyclerAdapter;
import com.example.toni.movielist.view.MoviesView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopRatedMoviesFragment extends Fragment implements MoviesView, MovieClickListener {

    @Inject
    MoviesPresenter presenter;

    @BindView(R.id.movies_recyclerview)
    RecyclerView moviesRecyclerView;

    private List<Movie> movies;
    private MovieRecyclerAdapter movieRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.movies_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        App.getComponent().inject(this);
        presenter.setView(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        movieRecyclerAdapter = new MovieRecyclerAdapter(this);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Constants.SPAN_COUNT_RV));
        moviesRecyclerView.setAdapter(movieRecyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getTopRatedMovies(1);
    }

    public static Fragment newInstance() {
        return new TopRatedMoviesFragment();
    }

    @Override
    public void showMovies(MovieResponse movieResponse) {
        movies = movieResponse.getMovies();
        movieRecyclerAdapter.updateMovies(movies);
    }

    @Override
    public void onMovieClicked(int movieId) {

    }
}
