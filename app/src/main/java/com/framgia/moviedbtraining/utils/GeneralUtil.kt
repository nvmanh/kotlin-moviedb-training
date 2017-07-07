package com.framgia.moviedbtraining.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View

object GeneralUtil {

  fun checkInternet(context: Context): Boolean {
    val cd = ConnectionDetector(context)
    return cd.isConnectingToInternet
  }

  fun isNetworkAvailable(context: Context): Boolean {
    return GeneralUtil.checkInternet(context)
  }

  fun showSnackbar(view: View, message: String) {
    var snackbar: Snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    snackbar.show()
  }
}