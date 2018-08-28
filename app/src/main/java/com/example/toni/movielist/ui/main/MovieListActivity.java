package com.example.toni.movielist.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.ui.main.adapter.MoviePagerAdapter;
import com.example.toni.movielist.ui.main.fragments.favorite.FavoriteMoviesFragment;
import com.example.toni.movielist.ui.main.fragments.categories.MoviesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivity extends AppCompatActivity{
    
    @BindView(R.id.movie_list_viewpager)
    ViewPager viewPager;

    @BindView(R.id.movie_list_tab_layout)
    TabLayout tabLayout;

    MoviePagerAdapter moviePagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        setupViewPager();
    }

    private void setupViewPager() {
        moviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        moviePagerAdapter.addItem(MoviesFragment.newInstance(Constants.MOVIE_TYPE_UPCOMING), Constants.MOVIE_TYPE_UPCOMING);
        moviePagerAdapter.addItem(MoviesFragment.newInstance(Constants.MOVIE_TYPE_NOW_PLAYING), Constants.MOVIE_TYPE_NOW_PLAYING);
        moviePagerAdapter.addItem(MoviesFragment.newInstance(Constants.MOVIE_TYPE_TOP_RATED), Constants.MOVIE_TYPE_TOP_RATED);
        moviePagerAdapter.addItem(new FavoriteMoviesFragment(), Constants.MOVIE_TYPE_FAVORITE);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(moviePagerAdapter);
    }

}
