package com.example.toni.movielist.interaction;


import com.example.toni.movielist.Constants;
import com.example.toni.movielist.network.NetworkResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FirebaseInteractorImpl implements FirebaseInteractor{

    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    public FirebaseInteractorImpl(DatabaseReference databaseReference){
        this.databaseReference = databaseReference;
    }


    @Override
    public void getFavoriteMovieIds(final NetworkResponse<List<Integer>> callback, final String userId) {
        databaseReference.addListenerForSingleValueEvent(valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Integer>> genericTypeIndicator = new GenericTypeIndicator<List<Integer>>() {};
                List<Integer> movieIds = dataSnapshot.child(userId).child(Constants.MOVIE_ID_FB_PATH).getValue(genericTypeIndicator);
                if (movieIds != null){
                    callback.onSuccess(movieIds);
                }else {
                    callback.onFailure(new NullPointerException());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void setFavoriteMovieIds(List<Integer> movieIds, String userId) {
        databaseReference.child(userId).child(Constants.MOVIE_ID_FB_PATH).setValue(movieIds);
    }

    @Override
    public void onDestroy() {
        if (databaseReference != null && valueEventListener != null){
            databaseReference.removeEventListener(valueEventListener);
        }
    }

}
