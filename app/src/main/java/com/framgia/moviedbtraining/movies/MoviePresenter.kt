package com.framgia.moviedbtraining.movies

import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.rest.RequestHelper
import com.framgia.moviedbtraining.utils.GeneralUtil
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener

class MoviePresenter(private var type: String,
    private val mViewModel: MovieContractNew.ViewModel) : MovieContractNew.Presenter {

  private var mPage: Int = 1
  private var isEmpty: Boolean = false
  private var onLoadMoreListener: EndlessRecyclerOnScrollListener? = null
  private var mMovieList: List<Movie>? = null

  override fun getMovies() {
    getMoviesbyType(type, mPage)
  }

  override fun addEndlessListener() {
    addEndlessRecyclerOnScrollListener()
    mViewModel.addEndlessListener(onLoadMoreListener!!)
  }

  private fun getMoviesbyType(type: String, page: Int) {
    if ((!GeneralUtil.isNetworkAvailable())) {
      mViewModel.hideLoading()
      mViewModel.showSnack(App.self().getString(R.string.err_network))
      return
    }
    mMovieList = RequestHelper.getRequesMoviesByType(type, page, mViewModel)!!
  }

  private fun addEndlessRecyclerOnScrollListener() {
    onLoadMoreListener = object : EndlessRecyclerOnScrollListener() {
      override fun onLoadMore() {
        if (isEmpty) {
          return
        }
        mPage++
        getMoviesbyType(type, mPage)
      }
    }
  }
}