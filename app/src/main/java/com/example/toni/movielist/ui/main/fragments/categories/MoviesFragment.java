package com.example.toni.movielist.ui.main.fragments.categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.toni.movielist.App;
import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.listener.MovieClickListener;
import com.example.toni.movielist.listener.MoviesScrollListener;
import com.example.toni.movielist.model.MovieResponse;
import com.example.toni.movielist.presentation.MoviesPresenter;
import com.example.toni.movielist.ui.details.DetailsActivity;
import com.example.toni.movielist.ui.login.LoginActivity;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManagerImpl;
import com.example.toni.movielist.ui.main.fragments.adapter.MovieRecyclerAdapter;
import com.example.toni.movielist.ui.search.SearchActivity;
import com.example.toni.movielist.util.NetworkUtil;
import com.example.toni.movielist.view.MoviesView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesFragment extends Fragment implements MoviesView, MovieClickListener{

    @Inject
    MoviesPresenter presenter;

    @BindView(R.id.movies_recyclerview)
    RecyclerView moviesRecyclerView;

    @BindView(R.id.movies_swipetorefresh)
    SwipeRefreshLayout refreshLayout;

    private MovieRecyclerAdapter movieRecyclerAdapter;
    private int currentPage = Constants.MOVIES_FIRST_PAGE;
    private boolean isLoading;
    private MenuItem menuItemSearch;
    private String currentFragmentName;

    public static MoviesFragment newInstance(String currentFragment) {
        MoviesFragment upcomingMoviesFragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(Constants.CURRENT_FRAGMENT, currentFragment);
        upcomingMoviesFragment.setArguments(args);
        return upcomingMoviesFragment;
    }

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
        setHasOptionsMenu(true);
        getCurrentFragmentName();
        presenter.setView(this);
        initRecyclerView();
        refreshLayout.setOnRefreshListener(() -> getMoviesFirstPage());
    }

    private void getCurrentFragmentName() {
        currentFragmentName = getArguments().getString(Constants.CURRENT_FRAGMENT);
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

    public void changeLoadingState(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public void onLogoutMenuItemClicked() {
        presenter.logoutUser(GoogleLoginManagerImpl.isUserLoggedIn(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        getMoviesFirstPage();
    }

    private void getMoviesFirstPage(){
        presenter.getMovies(Constants.MOVIES_FIRST_PAGE, currentFragmentName, NetworkUtil.isNetworkConnected(getActivity()));
    }

    private void getMoviesNextPage(){
        presenter.getMovies(currentPage, currentFragmentName, NetworkUtil.isNetworkConnected(getActivity()));
    }

    @Override
    public void showMovies(MovieResponse movieResponse) {
        movieRecyclerAdapter.updateMovies(movieResponse.getMovies());
    }

    @Override
    public void showMoviesNextPage(MovieResponse movieResponse) {
        changeLoadingState(false);
        movieRecyclerAdapter.addMoreMovies(movieResponse.getMovies());
    }

    @Override
    public void hideRefreshingBar() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showLogoutSuccessMessage() {
        Toast.makeText(getActivity(), R.string.logout_success_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLogoutFailedMessage() {
        Toast.makeText(getActivity(), R.string.logout_failed_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void onMovieClicked(int movieId) {
        startDetailsActivity(movieId);
        closeSearchView();
    }

    private void startDetailsActivity(int movieId) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Constants.MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        menuItemSearch = menu.findItem(R.id.options_search_menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.options_search_menu));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearchActivity(query);
                closeSearchView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.handleOnSearchTextChange(newText);
                return false;
            }
        });
    }

    private void closeSearchView() {
        menuItemSearch.collapseActionView();
    }

    private void startSearchActivity(String query) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra(Constants.SEARCH_QUERY, query);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.handleOnOptionsItemSelected(item);
        return true;
    }

    @Override
    public void showLoginRequiredMessage() {
        Toast.makeText(getActivity(), getResources().getString(R.string.login_first_error_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkErrorMessage() {
        Toast.makeText(getActivity(), R.string.no_internet_connection_text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUiWithSearchedResults(MovieResponse movieResponse) {
        movieRecyclerAdapter.updateMovies(movieResponse.getMovies());
    }

    @Override
    public void setupUiAfterSearchIsFinished() {
        getMoviesFirstPage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribe();
    }
}
