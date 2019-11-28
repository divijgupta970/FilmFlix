package com.example.filmflix.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static String BASE_URL="https://api.themoviedb.org/3/";
    private static Retrofit retrofit=null;

    public static MovieDataService getService(){
       if (retrofit==null){
           retrofit=new Retrofit
                   .Builder()
                   .baseUrl(BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
       }
       return retrofit.create(MovieDataService.class);
    }
}
