package com.framgia.moviedbtraining.utils

import android.content.SharedPreferences
import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.constants.Keys
import com.framgia.moviedbtraining.model.User
import com.google.gson.Gson


class ApplicationPrefs() {
  private val mPreferences: SharedPreferences = App.self().getSharedPreferences(
      Keys.APPLICATION_PREFS, 0)

  fun setSharedPrefs(key: String, id: String) {
    val prefs = mPreferences.edit()
    prefs.putString(key, id)
    prefs.apply()
  }

  fun getPrefData(value: String): String {
    return mPreferences.getString(value, "")
  }

  fun getPrefBooleanData(value: String): Boolean {
    return mPreferences.getBoolean(value, false)
  }

  fun setSessionId(sId: String) {
    val mEditor = mPreferences.edit()
    mEditor.putString(Keys.SESSION_ID, sId).apply()
  }

  fun setIsLogin(isLogin: Boolean) {
    val mEditor = mPreferences.edit()
    mEditor.putBoolean(Keys.IS_LOGIN, isLogin).apply()
  }

  fun getLoginStatus(): Boolean {
    return getPrefBooleanData(Keys.IS_LOGIN)
  }

  fun setUser(user: User) {
    val gson = Gson()
    val jsonUser = gson.toJson(user)
    setSharedPrefs(Keys.USER, jsonUser)
    setIsLogin(true)
  }

  fun getUser(): User {
    val gson = Gson()
    val json = getPrefData(Keys.USER)
    val jsonUser = gson.fromJson<User>(json, User::class.java)
    return jsonUser
  }
}