package com.framgia.moviedbtraining.search

import com.framgia.moviedbtraining.base.BaseView
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener

/**
 * Created by FRAMGIA\pham.duc.nam on 10/07/2017.
 */

interface SearchContract {
  interface ViewModel : BaseView<Presenter> {
    fun showResult(movie: List<Movie>)
    fun showSnack(message: String)
    fun addEndlessListener(listener: EndlessRecyclerOnScrollListener)
  }

  interface Presenter {
    fun getResult(text: String, year: String)
    fun addEndlessListener()
  }
}
