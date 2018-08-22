package com.example.toni.movielist.ui.details;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.toni.movielist.App;
import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.model.MovieDetailsResponse;
import com.example.toni.movielist.presentation.MovieDetailsPresenter;
import com.example.toni.movielist.view.MovieDetailsView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends Activity implements MovieDetailsView{


    @Inject
    MovieDetailsPresenter presenter;

    @BindView(R.id.details_toolbar)
    Toolbar toolbar;

    @BindView(R.id.details_vote_textview)
    TextView voteTv;

    @BindView(R.id.details_release_date_textview)
    TextView dateTv;

    @BindView(R.id.details_overview_textview)
    TextView overviewTv;

    @BindView(R.id.details_poster_imageview)
    ImageView posterIv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        App.getComponent().inject(this);
        presenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMovieDetails();
    }

    public void getMovieDetails() {
        presenter.getMovieDetails(getMovieIdFromIntent());
    }

    @Override
    public void showMovieDetails(MovieDetailsResponse details) {
        setUpDetails(details);
    }

    private void setUpDetails(MovieDetailsResponse details) {
        String posterUrl = Constants.IMAGES_BASE_URL + details.getPosterPath();
        Glide.with(getApplicationContext()).load(posterUrl).into(posterIv);
        voteTv.setText(String.valueOf(details.getVote()));
        dateTv.setText(String.valueOf(details.getReleaseDate()));
        overviewTv.setText(String.valueOf(details.getOverview()));
        toolbar.setTitle(details.getTitle());
    }

    public int getMovieIdFromIntent() {
        if (getIntent().hasExtra(Constants.MOVIE_ID)){
            return getIntent().getIntExtra(Constants.MOVIE_ID, -1);
        }
        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.details_refresh_menu:
                break;
            case R.id.details_logout_menu:
                break;
        }
        return true;
    }
}
