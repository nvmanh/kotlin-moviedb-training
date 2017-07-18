package com.framgia.moviedbtraining.rest

import com.framgia.moviedbtraining.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

  @GET("movie/{type}")
  fun getMovies(@Path("type") type: String, @Query("api_key") apiKey: String,
      @Query("page") page: Int): Call<MoviesResponse>

  @GET("authentication/token/new")
  fun getToken(@Query("api_key") apiKey: String): Call<ServiceResponse>

  @GET("authentication/session/new")
  fun getSessionId(@Query("api_key") apiKey: String,
      @Query("request_token") token: String): Call<ServiceResponse>

  @GET("authentication/token/validate_with_login")
  fun authWithLogin(@Query("api_key") apiKey: String, @Query("username") username: String,
      @Query("password") password: String,
      @Query("request_token") token: String): Call<ServiceResponse>

  @GET("account")
  fun getAccount(@Query("api_key") apiKey: String,
      @Query("session_id") sessionId: String): Call<User>

  @GET("account/{account_id}/favorite/movies")
  fun getFavourites(@Query("api_key") apiKey: String, @Query("session_id") sessionId: String,
      @Query("language") language: String,
      @Query("page") page: Int): Call<MoviesResponse>

  @GET("account/{account_id}/rated/movies")
  fun getRated(@Query("api_key") apiKey: String, @Query("session_id") sessionId: String,
      @Query("language") language: String,
      @Query("page") page: Int): Call<MoviesResponse>

  @GET("movie/{movie_id}/similar")
  fun getSimilar(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String,
      @Query("language") language: String,
      @Query("page") page: Int): Call<MoviesResponse>

  @GET("search/movie")
  fun getResultSearch(@Query("api_key") apiKey: String,
      @Query("query") query: String,
      @Query("page") page: Int,
      @Query("primary_release_year") year: String): Call<MoviesResponse>

  @GET("movie/{movie_id}")
  fun getMovieDetails(@Path("movie_id") mId: Int,
      @Query("api_key") apiKey: String): Call<MovieDetails>

  @GET("movie/{movie_id}/images")
  fun getMovieImages(@Path("movie_id") mId: Int, @Query("api_key") apiKey: String,
      @Query("language") language: String): Call<MovieDetails>

  @GET("genre/movie/list")
  fun getGenres(@Query("api_key") apiKey: String,
      @Query("language") language: String): Call<GenresResponse>

  @Headers("Content-type: application/json")
  @POST("movie/{movie_id}/rating")
  fun rateMovie(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String,
      @Query("session_id") sessionId: String,
      @Query("value") rateValue: Number): Call<MoviesResponse>

  @Headers("Content-type: application/json")
  @DELETE("movie/{movie_id}/rating")
  fun deleteRatedMovie(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String,
      @Query("session_id") sessionId: String): Call<MoviesResponse>
}