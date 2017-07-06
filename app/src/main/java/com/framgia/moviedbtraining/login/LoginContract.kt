package com.framgia.moviedbtraining.login

import com.framgia.moviedbtraining.base.BaseView

interface LoginContract {
  interface ViewModel : BaseView<Presenter> {
    fun showSnack(message: String)
    fun login(username: String, password: String)
  }

  interface Presenter {
    fun getToken()
    fun getSessionId()
    fun doLogin(username: String, password: String)
  }
}