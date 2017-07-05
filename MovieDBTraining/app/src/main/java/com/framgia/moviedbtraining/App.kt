package com.framgia.moviedbtraining

import android.app.Application

/**
 * Created by FRAMGIA\babatunde.fatoye.sunday on 7/4/17.
 */

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    mSelf = this
  }

  companion object {
    private var mSelf: App? = null

    fun self(): App {
      return mSelf!!
    }
  }
}