package com.framgia.moviedbtraining.movieDetails

import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.rest.RequestHelper
import com.framgia.moviedbtraining.utils.GeneralUtil

class MovieDetailsPresenter(
    private val mViewModel: MovieDetailsContract.ViewModel, movieId: Int) : MovieDetailsContract.Presenter {
  override fun rateMovie(mId: Int, toFloat: Float) {
    if ((!GeneralUtil.isNetworkAvailable())) {
      mViewModel.hideLoading()
      mViewModel.showSnack(App.self().getString(R.string.err_network))
      return
    }
    RequestHelper.rateMovie(mId, toFloat, mViewModel)
  }

  override fun deleteRatedMovie(mId: Int) {
    if ((!GeneralUtil.isNetworkAvailable())) {
      mViewModel.hideLoading()
      mViewModel.showSnack(App.self().getString(R.string.err_network))
      return
    }
    RequestHelper.deleteRatedMovie(mId, mViewModel)
  }

  private var mMovieId: Int = movieId

  override fun getMovieDetails() {
    getMovieDetails(mMovieId)
  }

  private fun getMovieDetails(movieId: Int) {
    if ((!GeneralUtil.isNetworkAvailable())) {
      mViewModel.hideLoading()
      mViewModel.showSnack(App.self().getString(R.string.err_network))
      return
    }
    RequestHelper.getMovieDetails(movieId, mViewModel)
  }
}