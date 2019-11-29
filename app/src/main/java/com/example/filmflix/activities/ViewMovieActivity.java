package com.example.filmflix.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.filmflix.R;
import com.example.filmflix.model.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewMovieActivity extends AppCompatActivity {
    private Result movie;
    private ImageView ivPoster;
    private TextView tvTitle,tvDesc,tvDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);
        postponeEnterTransition();
        tvTitle=findViewById(R.id.tvTitle);
        tvDesc=findViewById(R.id.tvDesc);
        tvDate=findViewById(R.id.tvReleaseDate);
        ivPoster=findViewById(R.id.ivPoster);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        movie=intent.getParcelableExtra("movie");
        getSupportActionBar().setTitle(movie.getTitle());

        tvTitle.setText(movie.getTitle());
        tvDesc.setText(movie.getOverview());

        String imagePath="https://image.tmdb.org/t/p/w500"+movie.getPosterPath();
        Glide.with(this)
                .load(imagePath)
                .placeholder(getDrawable(R.drawable.placeholder))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(ivPoster);

        try {
            Date dateInitial=new SimpleDateFormat("yyyy-MM-dd").parse(movie.getReleaseDate());
            String pattern="dd MMMM, yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String dateConverted = "Released on "+simpleDateFormat.format(dateInitial);
            tvDate.setText(dateConverted);
        } catch (ParseException e) {
            e.printStackTrace();
            String s="Released on"+movie.getReleaseDate();
            tvDate.setText(s);
        }

    }

}
