package com.example.filmflix.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.filmflix.model.MovieRepository;
import com.example.filmflix.model.Result;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository=new MovieRepository(application);
    }
    public LiveData<List<Result>> getAllMovies(){
        return movieRepository.getMutableLiveData();
    }
}
