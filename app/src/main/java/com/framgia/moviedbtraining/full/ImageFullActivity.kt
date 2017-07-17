package com.framgia.moviedbtraining.full

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.MovieDetails
import kotlinx.android.synthetic.main.activity_image_full.*

class ImageFullActivity : AppCompatActivity() {

  private var mImageUrl: String = ""
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_image_full)
    getExtras()
    ivClose.setOnClickListener { this.finish() }
  }

  fun getExtras() {
    mImageUrl = intent.getStringExtra(IMAGE_EXTRA)
    if (TextUtils.isEmpty(mImageUrl)) return
    Glide.with(this).load(mImageUrl).into(ivPoster)
  }

  companion object {
    private const val IMAGE_EXTRA = "image_extra"
    fun Context.MovieDetailsIntent(movies : MovieDetails) : Intent{
      return Intent(this, ImageFullActivity::class.java).apply {
        putExtra(IMAGE_EXTRA, Constants.WEB_URL + movies.posterPath)
      }
    }
  }
}