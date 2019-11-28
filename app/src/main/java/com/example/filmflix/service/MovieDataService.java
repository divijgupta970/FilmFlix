package com.example.filmflix.service;

import com.example.filmflix.model.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {
    @GET("movie/popular")
    Call<Movie> getPopularMovies(@Query("api_key") String apiKey);
}
