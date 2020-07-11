package com.techiasolutions.popularmovies;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface MoviesService {
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

}