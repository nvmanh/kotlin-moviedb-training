package com.framgia.moviedbtraining.profile

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.constants.Keys
import com.framgia.moviedbtraining.model.User
import com.framgia.moviedbtraining.utils.ApplicationPrefs
import com.framgia.moviedbtraining.utils.CircleTransform
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
  private var mUser: User? = null
  private val PROFILE_SIZE: Float = 0.5f

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile)

    init()
    loadUserProfile(mUser!!)
  }

  fun init() {
    val mPref: ApplicationPrefs = ApplicationPrefs()
    if (mPref.getLoginStatus()) {
      mUser = mPref.getUser()
    }

    ivClose.setOnClickListener { finish() }
    btnLogout.setOnClickListener { logout() }
  }

  fun loadUserProfile(user: User) {
    Glide.with(this).load(
        Constants.GRAVATAR_URL + user.avatar!!.gravatar!!.hash + Constants.GRAVATAR_SIZE)
        .crossFade()
        .thumbnail(PROFILE_SIZE)
        .bitmapTransform(CircleTransform(this))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(ivProfile)
    tvUsername.text = user.username
    if (!TextUtils.isEmpty(user.name))
      tvName.text = user.name
  }

  fun logout() {
    val preferences: SharedPreferences = App.self().getSharedPreferences(Keys.APPLICATION_PREFS, 0)
    preferences.edit().clear().apply()
    val returnIntent = intent
    setResult(Activity.RESULT_OK, returnIntent)
    finish()
  }
}
