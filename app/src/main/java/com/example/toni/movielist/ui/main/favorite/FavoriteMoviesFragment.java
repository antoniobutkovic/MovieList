package com.example.toni.movielist.ui.main.favorite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toni.movielist.R;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManager;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManagerImpl;

public class FavoriteMoviesFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movies_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!GoogleLoginManagerImpl.isUserLoggedIn(getActivity())){
            Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.login_first_error_message), Snackbar.LENGTH_LONG).setAction("LOGIN", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        }
    }
}
