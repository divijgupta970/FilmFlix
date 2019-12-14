package com.example.filmflix.activities;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmflix.R;
import com.example.filmflix.adapters.PopularMoviesAdapter;
import com.example.filmflix.databinding.ActivityMainBinding;
import com.example.filmflix.model.Result;
import com.example.filmflix.viewmodels.MainActivityViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PagedList<Result> movies;
    private ProgressBar progressBar;
    private MainActivityViewModel mainActivityViewModel;
    private boolean isNightModeOn;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MaterialToolbar toolbar = activityMainBinding.tbMain;
        setSupportActionBar(toolbar);

        recyclerView = activityMainBinding.rvMain;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        else
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        sharedPref = getPreferences(MODE_PRIVATE);
        boolean defaultValue = false;
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                defaultValue = false;
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                defaultValue = true;
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                defaultValue = false;
        }
        isNightModeOn = sharedPref.getBoolean("isNightModeOn", defaultValue);
        Log.d("Testing123", "isNightModeOn:" + isNightModeOn);

        progressBar = activityMainBinding.pbMain;
        progressBar.setVisibility(View.VISIBLE);
        Log.d("Testing123","VISIBLE");
        getPopularMovies();

    }


    private void getPopularMovies() {
        mainActivityViewModel.getMoviesPagedList().observe(this, new Observer<PagedList<Result>>() {
            @Override
            public void onChanged(PagedList<Result> results) {
                movies=results;
                setUpRecyclerView();
            }
        });
    }

    private void setUpRecyclerView() {
        PopularMoviesAdapter adapter = new PopularMoviesAdapter(this);
        adapter.submitList(movies);
        recyclerView.setAdapter(adapter);
        Log.d("Testing123","INVISIBLE");
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem nightMode = menu.findItem(R.id.nightMode);
        nightMode.setChecked(isNightModeOn);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences.Editor editor;
        switch (item.getItemId()) {
            case R.id.nightMode:
                if (item.isChecked()) {
                    isNightModeOn = !item.isChecked(); //Turn off night mode
                    item.setChecked(isNightModeOn);
                    editor = sharedPref.edit();
                    editor.putBoolean("isNightModeOn", isNightModeOn);
                    editor.apply();
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO);
                } else { //Turn on night mode
                    isNightModeOn = !item.isChecked(); //Turn off night mode
                    item.setChecked(isNightModeOn);
                    editor = sharedPref.edit();
                    editor.putBoolean("isNightModeOn", isNightModeOn);
                    editor.apply();
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();
                return true;
            default:
                return false;
        }
    }
}
