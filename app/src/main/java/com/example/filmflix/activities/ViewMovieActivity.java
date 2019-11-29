package com.example.filmflix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.filmflix.R;
import com.example.filmflix.model.Result;

public class ViewMovieActivity extends AppCompatActivity {
    private Result movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        if (intent.hasExtra("movie")){
            movie=intent.getParcelableExtra("movie");
            getSupportActionBar().setTitle(movie.getTitle());
        }else {
            Toast.makeText(this, "No extra", Toast.LENGTH_SHORT).show();
        }
    }

}
