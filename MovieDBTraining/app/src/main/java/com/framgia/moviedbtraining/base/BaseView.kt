package com.framgia.moviedbtraining.base

interface BaseView<T> {
  //set presenter for view
  fun setPresenter(t: T)

  fun showLoading()

  fun hideLoading()
}