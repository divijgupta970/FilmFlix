package com.example.filmflix.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.filmflix.R;
import com.example.filmflix.databinding.ActivityViewMovieBinding;
import com.example.filmflix.model.Result;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ViewMovieActivity extends AppCompatActivity {
    private ActivityViewMovieBinding activityViewMovieBinding;
    private Result movie;
    private ImageView ivPoster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityViewMovieBinding= DataBindingUtil.setContentView(this,R.layout.activity_view_movie);
        postponeEnterTransition();
        ivPoster=activityViewMovieBinding.ivPoster;

        Toolbar toolbar = activityViewMovieBinding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        movie=intent.getParcelableExtra("movie");
        activityViewMovieBinding.setMovie(movie);

        CollapsingToolbarLayout collapsingToolbarLayout = activityViewMovieBinding.collapsingToolbar;
        collapsingToolbarLayout.setTitle(movie.getTitle());


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

    }

}
