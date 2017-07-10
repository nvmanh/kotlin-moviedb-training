package com.framgia.moviedbtraining.movieDetails

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.MovieDetails
import com.framgia.moviedbtraining.utils.GeneralUtil
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsContract.ViewModel {

  lateinit var mPresenter: MovieDetailsPresenter
  private var mIntent: Intent? = null
  private var mId: Int = 0
  lateinit var mMovie: MovieDetails

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie_details)
    getExtras()
    init()
  }

  fun init() {
    mPresenter = MovieDetailsPresenter(this, mId)
    mPresenter.getMovieDetails()
  }

  fun getExtras() {
    mIntent = this.intent
    mId = mIntent!!.getIntExtra(Constants.EXTRA_MOVIE_ID, 0)
  }

  override fun showLoading() {
    pbHeaderProgress.visibility = View.VISIBLE
  }

  override fun hideLoading() {
    pbHeaderProgress.visibility = View.GONE
  }

  override fun showMovieDetail(movie: MovieDetails) {
    mMovie = movie
    rlLayoutAll.visibility = View.VISIBLE
  }

  override fun showSnack(message: String) {
    GeneralUtil.showSnackbar(pbHeaderProgress, message)
  }

  override fun showMoviePosters(moviePosters: List<MovieDetails.Posters>) {
//Todo fetch all posters
  }
}