package com.framgia.moviedbtraining.usermovies

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.utils.GeneralUtil
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.activity_user_movies.*

class UserMoviesActivity : AppCompatActivity(), UserMoviesContract.ViewModel {
  private var mAdapter: UserMoviesAdapter? = null
  private var mMovie: ArrayList<Movie> = arrayListOf()
  private lateinit var mIntent: Intent
  private var mType = ""
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_movies)
    getExtras()
    init()
    setToolbar()
  }

  fun getExtras() {
    mIntent = this.intent
    mType = mIntent.getStringExtra(Constants.TYPE)
  }

  fun init() {
    val mPresenter: UserMoviesPresenter = UserMoviesPresenter(this)
    mPresenter.getMovies(mType)
    mPresenter.addEndlessListener()
    mAdapter = UserMoviesAdapter(mMovie, R.layout.item_user_movies)
    rvMovies.layoutManager = LinearLayoutManager(
        this.applicationContext) as RecyclerView.LayoutManager?
    rvMovies.adapter = mAdapter
  }

  fun setToolbar() {
    setSupportActionBar(toolbar as Toolbar?)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    supportActionBar!!.setDisplayShowHomeEnabled(true)
    when (mType) {
      Constants.FAVOURITE_INTENT -> {
        supportActionBar!!.title = getString(R.string.str_fav_movies)
      }
      Constants.RATING_INTENT -> {
        supportActionBar!!.title = getString(R.string.str_rated_movies)
      }
    }
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
