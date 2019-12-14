package com.example.filmflix.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.filmflix.BuildConfig;
import com.example.filmflix.service.MovieDataService;
import com.example.filmflix.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long,Result> {

    private MovieDataService movieDataService;
    private Application application;

    public MovieDataSource(MovieDataService movieDataService, Application application) {
        this.movieDataService = movieDataService;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Result> callback) {

        movieDataService = RetrofitInstance.getService();
        Call<Movie> call= movieDataService.getPopularMoviesWithPaging(BuildConfig.apikey,1);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                ArrayList<Result>  movies;
                Movie movieResponse=response.body();
                if (movieResponse != null && movieResponse.getResults()!=null){
                    movies=(ArrayList<Result>)movieResponse.getResults();
                    callback.onResult(movies,null,(long)2);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Result> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Result> callback) {

        movieDataService = RetrofitInstance.getService();
        Call<Movie> call= movieDataService.getPopularMoviesWithPaging(BuildConfig.apikey,params.key);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                ArrayList<Result>  movies;
                Movie movieResponse=response.body();
                if (movieResponse != null && movieResponse.getResults()!=null){
                    movies=(ArrayList<Result>)movieResponse.getResults();
                    callback.onResult(movies,params.key+1);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }
}
