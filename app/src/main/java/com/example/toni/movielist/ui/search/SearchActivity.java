package com.example.toni.movielist.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.toni.movielist.App;
import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.listener.MovieClickListener;
import com.example.toni.movielist.listener.MoviesScrollListener;
import com.example.toni.movielist.model.MovieResponse;
import com.example.toni.movielist.presentation.SearchPresenter;
import com.example.toni.movielist.ui.details.DetailsActivity;
import com.example.toni.movielist.ui.search.adapter.SearchRecyclerAdapter;
import com.example.toni.movielist.view.SearchView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchView, MovieClickListener {

    @Inject
    SearchPresenter presenter;

    @BindView(R.id.search_recycler_view)
    RecyclerView recyclerView;

    private String searchQuery;
    private SearchRecyclerAdapter searchRecyclerAdapter;
    private boolean isLoading;
    private int currentPage = Constants.MOVIES_FIRST_PAGE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        App.getComponent().inject(this);
        presenter.setView(this);

        getDataFromIntent();
        setToolbarTitle();
        getSearchedMovies();
        initRecyclerView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        presenter.handleDataFromIntent(intent);
    }

    private void getSearchedMovies() {
        presenter.getSearchedMovies(Constants.MOVIES_FIRST_PAGE, searchQuery);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(searchQuery);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchRecyclerAdapter = new SearchRecyclerAdapter(this);
        recyclerView.setOnScrollListener(new MoviesScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                incrementCurrentPage();
                getSearchedMoviesNextPage();
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

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchRecyclerAdapter);
    }

    private void incrementCurrentPage() {
        currentPage++;
    }

    private void getSearchedMoviesNextPage() {
        presenter.getSearchedMovies(currentPage, searchQuery);
    }

    private void changeLoadingState(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.handleOnOptionsItemSelected(item);
        return true;
    }

    @Override
    public void showMovies(MovieResponse movieResponse) {
        searchRecyclerAdapter.updateMovies(movieResponse.getMovies());
    }

    @Override
    public void showMoviesNextPage(MovieResponse movieResponse) {
        changeLoadingState(false);
        searchRecyclerAdapter.addMoreMovies(movieResponse.getMovies());
    }

    @Override
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @Override
    public void onMenuBackItemClicked() {
        finish();
    }

    @Override
    public void onMovieClicked(int movieId) {
        startDetailsActivity(movieId);
    }

    private void startDetailsActivity(int movieId) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constants.MOVIE_ID, movieId);
        startActivity(intent);
    }
}
