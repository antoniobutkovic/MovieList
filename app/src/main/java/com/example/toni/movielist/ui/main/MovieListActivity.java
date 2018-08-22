package com.example.toni.movielist.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.ui.main.adapter.MoviePagerAdapter;
import com.example.toni.movielist.ui.main.favorite.FavoriteMoviesFragment;
import com.example.toni.movielist.ui.main.nowplaying.NowPlayingMoviesFragment;
import com.example.toni.movielist.ui.main.toprated.TopRatedMoviesFragment;
import com.example.toni.movielist.ui.main.upcoming.UpcomingMoviesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivity extends AppCompatActivity {

    MoviePagerAdapter moviePagerAdapter;

    @BindView(R.id.movie_list_viewpager)
    ViewPager viewPager;

    @BindView(R.id.movie_list_tab_layout)
    TabLayout tabLayout;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        setupViewPager();
    }

    private void setupViewPager() {
        moviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        moviePagerAdapter.addItem(new UpcomingMoviesFragment(), Constants.MOVIE_TYPE_UPCOMING);
        moviePagerAdapter.addItem(new NowPlayingMoviesFragment(), Constants.MOVIE_TYPE_NOW_PLAYING);
        moviePagerAdapter.addItem(new TopRatedMoviesFragment(), Constants.MOVIE_TYPE_TOP_RATED);
        moviePagerAdapter.addItem(new FavoriteMoviesFragment(), Constants.MOVIE_TYPE_FAVORITE);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(moviePagerAdapter);
    }
}
