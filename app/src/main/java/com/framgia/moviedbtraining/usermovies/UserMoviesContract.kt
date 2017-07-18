package com.framgia.moviedbtraining.usermovies

import com.framgia.moviedbtraining.base.BaseView
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener

interface UserMoviesContract {
  interface ViewModel : BaseView<Presenter> {
    fun showMovies(movie: List<Movie>)
    fun showSnack(message: String)
    fun addEndlessListener(listener: EndlessRecyclerOnScrollListener)
  }

  interface Presenter {
    fun getMovies(type: String, movieId: Int)
    fun addEndlessListener()
  }
}