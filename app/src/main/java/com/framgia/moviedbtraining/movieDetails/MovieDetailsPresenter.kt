package com.framgia.moviedbtraining.movieDetails

import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.utils.GeneralUtil

class MovieDetailsPresenter(
    private val mViewModel: MovieDetailsContract.ViewModel, movieId: Int) : MovieDetailsContract.Presenter {

  private var mMovieId: Int = movieId

  override fun getMovieDetails() {
    getMovieDetails(mMovieId)
  }

  private fun getMovieDetails(movieId: Int) {
    if ((!GeneralUtil.isNetworkAvailable(App.self()))) {
      mViewModel.hideLoading()
      mViewModel.showSnack(App.self().getString(R.string.err_network))
      return
    }
  }
}