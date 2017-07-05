package com.framgia.moviedbtraining

import android.app.Application
import android.content.Context

/**
 * Created by FRAMGIA\babatunde.fatoye.sunday on 7/4/17.
 */

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    mSelf = this
  }

  protected override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
  }

  companion object {
    private var mSelf: App? = null

    fun self(): App {
      return mSelf!!
    }
  }
}