package com.framgia.moviedbtraining.login

import com.framgia.moviedbtraining.base.BaseView
import com.framgia.moviedbtraining.model.User

interface LoginContract {
  interface ViewModel : BaseView<Presenter> {
    fun showSnack(message: String)
    fun login(username: String, password: String)
    fun userData(user: User)
  }

  interface Presenter {
    fun doLogin(username: String, password: String)
  }
}