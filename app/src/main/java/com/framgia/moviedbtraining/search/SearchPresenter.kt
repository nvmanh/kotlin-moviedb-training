package com.framgia.moviedbtraining.search

import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.rest.RequestHelper
import com.framgia.moviedbtraining.utils.GeneralUtil
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener

/**
 * Created by FRAMGIA\pham.duc.nam on 10/07/2017.
 */

class SearchPresenter(
    private val mViewModel: SearchContract.ViewModel) : SearchContract.Presenter {
  private var mPage: Int = 1
  private var mText: String = ""
  private var isEmpty: Boolean = false
  private var onLoadMoreListener: EndlessRecyclerOnScrollListener? = null
  private var mMovieList: List<Movie>? = null

  override fun getResult(text: String) {
    mText = text
    getResultMovies(mText, mPage)
  }

  override fun addEndlessListener() {
    addEndlessRecyclerOnScrollListener()
    mViewModel.addEndlessListener(onLoadMoreListener!!)
  }

  private fun addEndlessRecyclerOnScrollListener() {
    onLoadMoreListener = object : EndlessRecyclerOnScrollListener() {
      override fun onLoadMore() {
        if (isEmpty) {
          return
        }
        mPage++
        getResultMovies(mText, mPage)
      }
    }
  }

  private fun getResultMovies(text: String, page: Int){
    if ((!GeneralUtil.isNetworkAvailable(App.self()))) {
      mViewModel.hideLoading()
      mViewModel.showSnack(App.self().getString(R.string.err_network))
      return
    }
    mMovieList = RequestHelper.getSearchResult(text, page, mViewModel)!!
  }
}
