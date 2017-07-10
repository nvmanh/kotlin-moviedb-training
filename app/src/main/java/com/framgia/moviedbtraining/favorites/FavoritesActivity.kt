package com.framgia.moviedbtraining.favorites

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.utils.GeneralUtil
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : Activity(), FavouritesContract.ViewModel {
  private var mAdapter: FavouritesAdapter? = null
  private var mMovie: ArrayList<Movie> = arrayListOf()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_favorites)
    init()
  }

  fun init() {
    val mPresenter: FavouritesPresenter = FavouritesPresenter(this)
    mPresenter.getMovies()
    mPresenter.addEndlessListener()
    mAdapter = FavouritesAdapter(mMovie, R.layout.item_favourites)
    rvMovies.layoutManager = LinearLayoutManager(this.applicationContext) as RecyclerView.LayoutManager?
    rvMovies.adapter = mAdapter
  }

  override fun addEndlessListener(listener: EndlessRecyclerOnScrollListener) {
    rvMovies.addOnScrollListener(listener)
  }

  override fun showLoading() {
    pbHeaderProgress.visibility = View.VISIBLE
  }

  override fun hideLoading() {
    pbHeaderProgress.visibility = View.GONE
  }

  override fun showMovies(movie: List<Movie>) {
    mMovie.addAll(movie)
    mAdapter!!.notifyDataSetChanged()
  }

  override fun showSnack(message: String) {
    GeneralUtil.showSnackbar(rvMovies, message)
  }
}
