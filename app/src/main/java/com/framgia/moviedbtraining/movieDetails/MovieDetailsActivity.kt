package com.framgia.moviedbtraining.movieDetails

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.MovieDetails
import com.framgia.moviedbtraining.utils.GeneralUtil
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.util.*

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
    setMovie()
    rlLayoutAll.visibility = View.VISIBLE
  }

  override fun showSnack(message: String) {
    GeneralUtil.showSnackbar(pbHeaderProgress, message)
  }

  override fun showMoviePosters(moviePosters: List<MovieDetails.Posters>) {
    val mAdapter: PostersAdapter = PostersAdapter(moviePosters, R.layout.item_posters)
    rvMovieImages.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
    rvMovieImages.adapter = mAdapter
  }

  fun setMovie() {
    tvName.text = mMovie.title
    setValues(mMovie.status!!, tvStatus, tvStatusLabel)
    setValues(mMovie.releaseDate!!, tvReleaseDate, tvReleaseDateLabel)
    setValues(mMovie.budget.toString(), tvBudget, tvBudgetLabel)
    setValues(mMovie.revenue.toString(), tvRevenue, tvRevenueLabel)
    setValues(mMovie.runtime.toString(), tvRuntime, tvRuntimeLabel)
    setValues(mMovie.voteAverage.toString(), tvRating, tvRatingLabel)
    tvOverview.text = mMovie.overview
    tvGenere.text = getGenres()

    Glide.with(this).load(Constants.WEB_URL + mMovie.posterPath).into(ivPoster)
    Glide.with(this).load(Constants.WEB_URL + mMovie.backdropPath).asBitmap().into(
        object : SimpleTarget<Bitmap>() {
          override fun onResourceReady(resource: Bitmap,
              glideAnimation: GlideAnimation<in Bitmap>) {
            val drawable = BitmapDrawable(resource)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
              rlLayoutAll.background = drawable
            }
          }
        })
  }

  @SuppressLint("SetTextI18n")
  fun setValues(values: String, view: TextView, label: TextView) = if (!TextUtils.isEmpty(
      values) && values != "0") {
    view.text = values
    view.visibility = View.VISIBLE
    label.visibility = View.VISIBLE
  } else {
    view.visibility = View.GONE
    label.visibility = View.GONE
  }

  fun getGenres(): String? {
    var genres: String = ""
    val genreList: ArrayList<String> = arrayListOf()
    for (genre: MovieDetails.Genre in mMovie.genres!!) {
      genreList.add(genre.name!!)
      genres = android.text.TextUtils.join(", ", genreList)
    }
    return genres
  }
}