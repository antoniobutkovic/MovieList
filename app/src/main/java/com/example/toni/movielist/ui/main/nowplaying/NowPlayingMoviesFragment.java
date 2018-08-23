package com.example.toni.movielist.ui.main.nowplaying;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toni.movielist.App;
import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.listener.MovieClickListener;
import com.example.toni.movielist.listener.MoviesScrollListener;
import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.model.MovieResponse;
import com.example.toni.movielist.presentation.MoviesPresenter;
import com.example.toni.movielist.ui.details.DetailsActivity;
import com.example.toni.movielist.ui.main.adapter.MovieRecyclerAdapter;
import com.example.toni.movielist.view.MoviesView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NowPlayingMoviesFragment extends Fragment implements MoviesView, MovieClickListener{

    @Inject
    MoviesPresenter presenter;

    @BindView(R.id.movies_swipetorefresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.movies_recyclerview)
    RecyclerView moviesRecyclerView;

    private List<Movie> movies;
    private MovieRecyclerAdapter movieRecyclerAdapter;
    private int currentPage = Constants.MOVIES_FIRST_PAGE;
    private boolean isLoading;

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

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMoviesFirstPage();
            }
        });
    }


    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), Constants.SPAN_COUNT_RV);
        movieRecyclerAdapter = new MovieRecyclerAdapter(this);
        moviesRecyclerView.setOnScrollListener(new MoviesScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                incrementCurrentPage();
                getMoviesNextPage();
                changeLoadingState(true);
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        moviesRecyclerView.setLayoutManager(layoutManager);
        moviesRecyclerView.setAdapter(movieRecyclerAdapter);
    }

    private void incrementCurrentPage() {
        currentPage++;
    }

    private void changeLoadingState(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public void onResume() {
        super.onResume();
        getMoviesFirstPage();
    }

    private void getMoviesFirstPage() {
        presenter.getNowPlayingMovies(Constants.MOVIES_FIRST_PAGE);
    }

    private void getMoviesNextPage(){
        presenter.getUpcomingMovies(currentPage);
    }

    @Override
    public void showMovies(MovieResponse movieResponse) {
        movies = movieResponse.getMovies();
        movieRecyclerAdapter.updateMovies(movies);
    }

    @Override
    public void showMoviesNextPage(MovieResponse movieResponse) {
        changeLoadingState(false);
        movieRecyclerAdapter.addMovies(movieResponse.getMovies());
    }

    @Override
    public void hideRefreshingBar() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onMovieClicked(int movieId) {
        startDetailsActivity(movieId);
    }

    private void startDetailsActivity(int movieId) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Constants.MOVIE_ID, movieId);
        startActivity(intent);
    }
}
