package com.framgia.moviedbtraining.movieDetails

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.model.MovieDetails
import com.framgia.moviedbtraining.utils.GeneralUtil
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.util.*

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsContract.ViewModel {

  lateinit var mPresenter: MovieDetailsPresenter
  private var mIntent: Intent? = null
  private var mId: Int = 0
  private var mRating: Int = 0
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
    tvUserRate.setOnClickListener { showRateDialog() }
  }

  fun getExtras() {
    mIntent = this.intent
    mId = mIntent!!.getIntExtra(Constants.EXTRA_MOVIE_ID, 0)
    mRating = mIntent!!.getIntExtra(Constants.USER_RATING, 0)
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

    if (mRating != 0) {
      tvUserRate.text = mRating.toString()
    }
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

  private fun showRateDialog() {
    val dialog = Dialog(this)
    dialog.setContentView(R.layout.dialog_rate_movie)
    val text = dialog.findViewById(R.id.tvTitle) as TextView
    text.text = mMovie.title
    val btnCancel = dialog.findViewById(R.id.tvCancel) as TextView
    val btnRate = dialog.findViewById(R.id.tvRate) as TextView
    val ratingBar = dialog.findViewById(R.id.ratingBar) as RatingBar
    ratingBar.rating = mRating.toFloat()
    btnCancel.setOnClickListener {
      dialog.dismiss()
    }
    btnRate.setOnClickListener {
      if (ratingBar.rating == mRating.toFloat()) return@setOnClickListener
      if (ratingBar.rating==0f) {
        mPresenter.deleteRatedMovie(mId)
      } else {
        mPresenter.rateMovie(mId, ratingBar.rating)
      }
      tvUserRate.text = ratingBar.rating.toString()
      mRating = ratingBar.rating.toInt()
      dialog.dismiss()
    }
    dialog.show()
  }

  companion object {
    fun UserMoviesIntent(movie: Movie) {
      val intent = Intent(App.self(), MovieDetailsActivity::class.java)
      intent.putExtra(Constants.EXTRA_MOVIE_ID, movie.id!!)
      if (movie.rating != null) {
        intent.putExtra(Constants.USER_RATING, movie.rating!!.toInt())
      }
      App.self().startActivity(intent)
    }
  }
}