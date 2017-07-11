package com.framgia.moviedbtraining.usermovies

import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.rest.RequestHelper
import com.framgia.moviedbtraining.utils.GeneralUtil
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener

class UserMoviesPresenter(
    private val mViewModel: UserMoviesContract.ViewModel) : UserMoviesContract.Presenter {

  private var mPage: Int = 1
  private var isEmpty: Boolean = false
  private var onLoadMoreListener: EndlessRecyclerOnScrollListener? = null
  private var mMovieList: List<Movie>? = null
  private var mType = ""

  override fun getMovies(type:String) {
    mType = type
    getUserMovies(mPage, mType)
  }

  override fun addEndlessListener() {
    addEndlessRecyclerOnScrollListener()
    mViewModel.addEndlessListener(onLoadMoreListener!!)
  }

  private fun getUserMovies(page: Int, type: String) {
    if ((!GeneralUtil.isNetworkAvailable(App.self()))) {
      mViewModel.hideLoading()
      mViewModel.showSnack(App.self().getString(R.string.err_network))
      return
    }
    mMovieList = RequestHelper.getUserMovies(page, mType, mViewModel)!!
  }

  private fun addEndlessRecyclerOnScrollListener() {
    onLoadMoreListener = object : EndlessRecyclerOnScrollListener() {
      override fun onLoadMore() {
        if (isEmpty) {
          return
        }
        mPage++
        getUserMovies(mPage, mType)
      }
    }
  }
}