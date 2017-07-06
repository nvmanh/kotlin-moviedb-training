package com.framgia.moviedbtraining.rest

import com.framgia.moviedbtraining.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

  @GET("movie/now_playing")
  fun getNowPlaying(@Query("api_key") apiKey: String,
      @Query("page") page: Int): Call<MoviesResponse>
}