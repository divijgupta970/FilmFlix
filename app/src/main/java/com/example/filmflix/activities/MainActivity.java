package com.example.filmflix.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmflix.R;
import com.example.filmflix.adapters.PopularMoviesAdapter;
import com.example.filmflix.model.Result;
import com.example.filmflix.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PopularMoviesAdapter adapter;
    private List<Result> resultList;
    private MainActivityViewModel mainActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.rvMain);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);

        getPopularMovies();

    }

    private void getPopularMovies() {
        mainActivityViewModel.getAllMovies().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                resultList=results;
                setUpRecyclerView();
            }
        });
    }

    private void setUpRecyclerView() {
        adapter=new PopularMoviesAdapter(resultList,this);
        recyclerView.setAdapter(adapter);
    }
}
