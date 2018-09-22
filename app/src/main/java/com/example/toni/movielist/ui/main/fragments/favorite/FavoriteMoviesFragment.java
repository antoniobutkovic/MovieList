package com.example.toni.movielist.ui.main.fragments.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toni.movielist.App;
import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.listener.MovieClickListener;
import com.example.toni.movielist.listener.MoviesScrollListener;
import com.example.toni.movielist.model.Movie;
import com.example.toni.movielist.presentation.FavoriteMoviesPresenter;
import com.example.toni.movielist.presentation.FavoriteMoviesView;
import com.example.toni.movielist.ui.details.DetailsActivity;
import com.example.toni.movielist.ui.login.LoginActivity;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManagerImpl;
import com.example.toni.movielist.ui.main.fragments.adapter.MovieRecyclerAdapter;
import com.example.toni.movielist.ui.search.SearchActivity;
import com.example.toni.movielist.util.NetworkUtil;
import com.example.toni.movielist.util.SharedPrefsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteMoviesFragment extends Fragment implements FavoriteMoviesView, MovieClickListener {

    @Inject
    FavoriteMoviesPresenter presenter;

    @BindView(R.id.movies_recyclerview)
    RecyclerView moviesRecyclerView;

    @BindView(R.id.favorite_no_movies_textview)
    TextView noFavoriteMoviesTv;

    private List<Movie> movies;
    private MovieRecyclerAdapter movieRecyclerAdapter;
    private boolean isLoading;
    private MenuItem menuItemSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorite_movies_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        App.getComponent().inject(this);
        setHasOptionsMenu(true);
        presenter.setView(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), Constants.SPAN_COUNT_RV);
        movieRecyclerAdapter = new MovieRecyclerAdapter(this);
        moviesRecyclerView.setOnScrollListener(new MoviesScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                incrementCurrentPage();
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
    }

    @Override
    public void changeLoadingState(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public void filterResultsInAdapter(String newText) {
        movieRecyclerAdapter.getFilter().filter(newText);
    }

    @Override
    public void onLogoutMenuItemClicked() {
        presenter.logoutUser(GoogleLoginManagerImpl.isUserLoggedIn(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        movies = new ArrayList<>();
        getMovies();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void showLoginRequiredSnackbar() {
        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.login_first_error_message), Snackbar.LENGTH_LONG).setAction(R.string.snackbar_login_button_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        }).show();
    }

    @Override
    public void showNetworkErrorMessage() {
        Toast.makeText(getActivity(), R.string.no_internet_connection_text, Toast.LENGTH_SHORT).show();
    }

    private void getMovies() {
        String uid = SharedPrefsUtil.getPreferencesField(getActivity(), Constants.USER_LOGIN_TOKEN);
        boolean isUserLoggedIn = GoogleLoginManagerImpl.isUserLoggedIn(getActivity());
        boolean isNetworkConnected = NetworkUtil.isNetworkConnected(getActivity());
        presenter.getFavoriteMovies(uid, isUserLoggedIn, isNetworkConnected);
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
    public void showNoFavoriteMoviesMessage() {
        noFavoriteMoviesTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoFavoriteMoviesMessage() {
        noFavoriteMoviesTv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showEmptyScreen() {
        movieRecyclerAdapter.updateMovies(movies);
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

    private void startSearchActivity(String query) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra(Constants.SEARCH_QUERY, query);
        startActivity(intent);
    }

    private void closeSearchView() {
        menuItemSearch.collapseActionView();
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
    public void onUserVisible() {
        presenter.checkUserAuthState(GoogleLoginManagerImpl.isUserLoggedIn(getActivity()));
    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    public void showFavoriteMovies(Movie movie) {
        movies.add(movie);
        movieRecyclerAdapter.updateMovies(movies);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (presenter != null){
            presenter.handleFragmentVisibilityToUser(isVisibleToUser);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribe();
    }
}
