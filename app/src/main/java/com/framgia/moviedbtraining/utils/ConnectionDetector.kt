package com.framgia.moviedbtraining.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionDetector(private val context: Context) {
  val isConnectingToInternet: Boolean
    get() {
      val connectivity = context.getSystemService(
          Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val info = connectivity.allNetworkInfo
      if (info != null) {
        info
            .filter { it.state == NetworkInfo.State.CONNECTED }
            .forEach { return true }
      }

      return false
    }
}