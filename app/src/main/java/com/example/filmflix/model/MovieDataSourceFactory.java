package com.example.filmflix.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.filmflix.service.MovieDataService;

public class MovieDataSourceFactory extends DataSource.Factory {
    private MovieDataService movieDataService;
    private MovieDataSource movieDataSource;
    private Application application;
    private MutableLiveData<MovieDataSource> mutableLiveData;

    public MovieDataSourceFactory(MovieDataService movieDataService, Application application) {
        this.movieDataService = movieDataService;
        this.application = application;
        mutableLiveData=new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        movieDataSource=new MovieDataSource(movieDataService,application);
        mutableLiveData.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<MovieDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
