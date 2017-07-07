package com.framgia.moviedbtraining.movies

import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.model.MoviesResponse
import com.framgia.moviedbtraining.rest.ApiClient
import com.framgia.moviedbtraining.rest.ApiInterface
import com.framgia.moviedbtraining.rest.RequestHelper
import com.framgia.moviedbtraining.utils.GeneralUtil
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NowPlayingPresenter(
    private val mViewModel: NowPlayingContractNew.ViewModel) : NowPlayingContractNew.Presenter {

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
    mMovieList = RequestHelper.getNowPlaying(page, mViewModel)!!
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