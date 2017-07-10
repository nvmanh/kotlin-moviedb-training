package com.framgia.moviedbtraining.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

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

  fun hiddenKeyboard(context: Context, view: EditText) {
    val imm = context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }

  fun showKeyboard(context: Context, edt: EditText) {
    val imm = context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT)
  }
}