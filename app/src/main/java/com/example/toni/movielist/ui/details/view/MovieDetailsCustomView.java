package com.example.toni.movielist.ui.details.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toni.movielist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsCustomView extends LinearLayout{

    @BindView(R.id.custom_details_view_title)
    TextView titleTv;

    @BindView(R.id.custom_details_view_description)
    TextView descTv;


    public MovieDetailsCustomView(Context context) {
        super(context);
        init(context);
    }

    public MovieDetailsCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MovieDetailsCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MovieDetailsCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_details_view, this, true);
        ButterKnife.bind(this);
    }
    public void initFields(String title, String description){
        titleTv.setText(title);
        descTv.setText(description);
    }

}
