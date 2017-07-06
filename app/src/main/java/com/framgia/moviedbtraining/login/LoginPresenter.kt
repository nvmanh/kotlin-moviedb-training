package com.framgia.moviedbtraining.login

import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.rest.RequestHelper
import com.framgia.moviedbtraining.utils.GeneralUtil

class LoginPresenter(
    private val mViewModel: LoginContract.ViewModel) : LoginContract.Presenter {

  override fun doLogin(username: String, password: String) {
    if ((!GeneralUtil.isNetworkAvailable(App.self()))) {
      mViewModel.hideLoading()
      mViewModel.showSnack(App.self().getString(R.string.err_network))
      return
    }
    RequestHelper.getToken(mViewModel, username, password)
  }
}