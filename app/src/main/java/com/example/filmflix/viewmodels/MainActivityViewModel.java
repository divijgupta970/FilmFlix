package com.example.filmflix.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.filmflix.model.MovieDataSourceFactory;
import com.example.filmflix.model.Result;
import com.example.filmflix.service.MovieDataService;
import com.example.filmflix.service.RetrofitInstance;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivityViewModel extends AndroidViewModel {

    private LiveData<PagedList<Result>> moviesPagedList;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        MovieDataService movieDataService = RetrofitInstance.getService();
        MovieDataSourceFactory factory = new MovieDataSourceFactory(movieDataService, application);

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();

        Executor executor = Executors.newFixedThreadPool(5);

        moviesPagedList = (new LivePagedListBuilder<Long, Result>(factory, config))
                .setFetchExecutor(executor)
                .build();
    }
    public LiveData<PagedList<Result>> getMoviesPagedList() {
        return moviesPagedList;
    }
}
