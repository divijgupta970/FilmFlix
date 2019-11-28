package com.example.filmflix.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.filmflix.BuildConfig;
import com.example.filmflix.R;
import com.example.filmflix.adapters.PopularMoviesAdapter;
import com.example.filmflix.model.Movie;
import com.example.filmflix.model.Result;
import com.example.filmflix.service.MovieDataService;
import com.example.filmflix.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PopularMoviesAdapter adapter;
    private List<Result> resultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.rvMain);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        MovieDataService movieDataService = RetrofitInstance.getService();
        Call<Movie> call = movieDataService.getPopularMovies(BuildConfig.apikey);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movieResponse=response.body();
                if (movieResponse != null && movieResponse.getResults()!=null){
                    resultList=movieResponse.getResults();
                }
                setUpRecyclerViews();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

    }

    private void setUpRecyclerViews() {
        adapter=new PopularMoviesAdapter(resultList,this);
        recyclerView.setAdapter(adapter);
    }
}
