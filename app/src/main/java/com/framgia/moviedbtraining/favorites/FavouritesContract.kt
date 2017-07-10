package com.framgia.moviedbtraining.favorites

import com.framgia.moviedbtraining.base.BaseView
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener

interface FavouritesContract {
  interface ViewModel : BaseView<Presenter> {
    fun showMovies(movie: List<Movie>)
    fun showSnack(message: String)
    fun addEndlessListener(listener: EndlessRecyclerOnScrollListener)
  }

  interface Presenter {
    fun getMovies()
    fun addEndlessListener()
  }
}