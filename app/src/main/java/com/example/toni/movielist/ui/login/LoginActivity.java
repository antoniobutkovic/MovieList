package com.example.toni.movielist.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.toni.movielist.App;
import com.example.toni.movielist.Constants;
import com.example.toni.movielist.R;
import com.example.toni.movielist.presentation.LoginPresenter;
import com.example.toni.movielist.ui.login.helper.GoogleLoginManager;
import com.example.toni.movielist.ui.main.MovieListActivity;
import com.example.toni.movielist.utils.NetworkUtils;
import com.example.toni.movielist.view.LoginView;
import com.google.android.gms.common.SignInButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView{

    @Inject
    LoginPresenter presenter;

    @Inject
    GoogleLoginManager googleLoginManager;

    @BindView(R.id.google_sign_in_button)
    SignInButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        App.getComponent().inject(this);

        presenter.setView(this);

        signInButton.setSize(SignInButton.SIZE_WIDE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkUserAuthState();
    }

    @OnClick(R.id.google_sign_in_button)
    public void onSignInBtnClicked(){
        if (NetworkUtils.isNetworkConnected(this)){
            signInWithGoogle();
        }else {
            showNetworkErrorMessage();
        }
    }

    private void showNetworkErrorMessage() {
        Toast.makeText(this, R.string.no_internet_connection_text, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.not_now_textview)
    public void onNotNowTvClicked(){
        startMovieListActivity();
    }


    @Override
    public void showUserLogInErrorMessage(String errorMsg) {

    }

    @Override
    public void startMovieListActivity() {
        startActivity(new Intent(this, MovieListActivity.class));
        finish();
    }


    private void signInWithGoogle() {
        startActivityForResult(googleLoginManager.getLoginIntent(), Constants.GOOGLE_SIGN_IN_RC);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.GOOGLE_SIGN_IN_RC) {
            presenter.loginUserWithGoogle(data);
        }
    }
}
