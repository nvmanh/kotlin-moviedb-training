package com.framgia.moviedbtraining.favorites

import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.rest.RequestHelper
import com.framgia.moviedbtraining.utils.GeneralUtil
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener

class FavouritesPresenter(
    private val mViewModel: FavouritesContract.ViewModel) : FavouritesContract.Presenter {

  private var mPage: Int = 1
  private var isEmpty: Boolean = false
  private var onLoadMoreListener: EndlessRecyclerOnScrollListener? = null
  private var mMovieList: List<Movie>? = null

  override fun getMovies() {
    getNowPlayingMovies(mPage)
  }

  override fun addEndlessListener() {
    addEndlessRecyclerOnScrollListener()
    mViewModel.addEndlessListener(onLoadMoreListener!!)
  }

  private fun getNowPlayingMovies(page: Int) {
    if ((!GeneralUtil.isNetworkAvailable(App.self()))) {
      mViewModel.hideLoading()
      mViewModel.showSnack(App.self().getString(R.string.err_network))
      return
    }
    mMovieList = RequestHelper.getFavourites(page, mViewModel)!!
  }

  private fun addEndlessRecyclerOnScrollListener() {
    onLoadMoreListener = object : EndlessRecyclerOnScrollListener() {
      override fun onLoadMore() {
        if (isEmpty) {
          return
        }
        mPage++
        getNowPlayingMovies(mPage)
      }
    }
  }
}