package com.framgia.moviedbtraining.movieDetails

import com.framgia.moviedbtraining.base.BaseView
import com.framgia.moviedbtraining.model.MovieDetails

interface MovieDetailsContract {
  interface ViewModel : BaseView<Presenter> {
    fun showMovieDetail(movie: MovieDetails)
    fun showMoviePosters(moviePosters: List<MovieDetails.Posters>)
    fun showSnack(message: String)
  }

  interface Presenter {
    fun getMovieDetails()
  }
}