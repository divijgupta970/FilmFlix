package com.example.filmflix.model;

import android.app.Application;


import androidx.lifecycle.MutableLiveData;

import com.example.filmflix.BuildConfig;
import com.example.filmflix.service.MovieDataService;
import com.example.filmflix.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private ArrayList<Result>  movies=new ArrayList<>();
    private MutableLiveData<List<Result>> mutableLiveData=new MutableLiveData<>();
    private Application application;

    public MovieRepository(Application application) {
        this.application = application;
    }
    public MutableLiveData<List<Result>> getMutableLiveData(){
        MovieDataService movieDataService = RetrofitInstance.getService();
        Call<Movie> call = movieDataService.getPopularMovies(BuildConfig.apikey);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movieResponse=response.body();
                if (movieResponse != null && movieResponse.getResults()!=null){
                    movies=(ArrayList<Result>)movieResponse.getResults();
                    mutableLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
