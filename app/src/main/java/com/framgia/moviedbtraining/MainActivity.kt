package com.framgia.moviedbtraining

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.favorites.FavoritesActivity
import com.framgia.moviedbtraining.login.LoginActivity
import com.framgia.moviedbtraining.model.User
import com.framgia.moviedbtraining.movies.MovieFragment
import com.framgia.moviedbtraining.profile.ProfileActivity
import com.framgia.moviedbtraining.rated.RatedActivity
import com.framgia.moviedbtraining.utils.ApplicationPrefs
import com.framgia.moviedbtraining.utils.CircleTransform
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main.*


class MainActivity : AppCompatActivity() {

  val TAG_NOW: Int = 0
  val TAG_POPULAR: Int = 1
  val TAG_TOP_RATED: Int = 2
  val TAG_UPCOMING: Int = 3

  private var activityTitles: Array<String>? = null
  val movieFragment: MovieFragment = MovieFragment()
  var navItemIndex = TAG_NOW
  private var mHandler: Handler? = null
  private var imgProfile: ImageView? = null
  private var tvUsername: TextView? = null
  private var imgSearch: ImageView? = null
  private val mFlagOnBackPress = true
  private val sizeProfile: Float = 0.5f
  private var mPref: ApplicationPrefs? = ApplicationPrefs()
  val PROFILE_INTENT: Int = 11
  val LOGIN_INTENT: Int = 22

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    setDefaultProfile()
    setUpNavigationView()
    setUpUser()
    activityTitles = resources.getStringArray(R.array.nav_item_activity_titles)
    mHandler = Handler()
    if (savedInstanceState == null) {
      navItemIndex = TAG_NOW
      loadMovieFragment()
    }
  }

  private fun loadMovieFragment() {
    mNaviView.menu.getItem(navItemIndex).setChecked(true)
    supportActionBar!!.setTitle(activityTitles!![navItemIndex])
    val mPendingRunnable = Runnable {
      val fragment = getMoviesFragment()
      val fragmentTransaction = supportFragmentManager.beginTransaction()
      fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
          android.R.anim.fade_out)
      fragmentTransaction.replace(R.id.frame, fragment)
      fragmentTransaction.commitAllowingStateLoss()
    }
    mHandler!!.post(mPendingRunnable)
    mDrawerLayout.closeDrawers()
    invalidateOptionsMenu()
  }

  private fun getMoviesFragment(): Fragment {
    return movieFragment.newinstance(navItemIndex)
  }

  private fun setUpNavigationView() {
    mNaviView.setNavigationItemSelectedListener(
        NavigationView.OnNavigationItemSelectedListener { menuItem ->
          when (menuItem.itemId) {
            R.id.nav_now_playing -> {
              navItemIndex = TAG_NOW
            }
            R.id.nav_popular -> {
              navItemIndex = TAG_POPULAR
            }
            R.id.nav_top_rated -> {
              navItemIndex = TAG_TOP_RATED
            }
            R.id.nav_upcoming -> {
              navItemIndex = TAG_UPCOMING
            }
            R.id.nav_favo -> {
              startActivity(FavoritesActivity())
              return@OnNavigationItemSelectedListener true
            }
            R.id.nav_rate -> {
              startActivity(RatedActivity())
              return@OnNavigationItemSelectedListener true
            }
            else -> navItemIndex = TAG_NOW
          }
          menuItem.isChecked = !menuItem.isChecked
          loadMovieFragment()
          true
        })

    val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
        R.string.openDrawer, R.string.closeDrawer) {

      override fun onDrawerClosed(drawerView: View?) {
        super.onDrawerClosed(drawerView)
      }

      override fun onDrawerOpened(drawerView: View?) {
        super.onDrawerOpened(drawerView)
      }
    }
    mDrawerLayout.addDrawerListener(actionBarDrawerToggle)
    actionBarDrawerToggle.syncState()
  }

  private fun setDefaultProfile() {
    imgProfile = mNaviView.getHeaderView(0).findViewById(R.id.img_profile) as ImageView
    imgSearch = mNaviView.getHeaderView(0).findViewById(R.id.img_search) as ImageView
    tvUsername = mNaviView.getHeaderView(0).findViewById(R.id.tv_username) as TextView
    Glide.with(this).load(getString(R.string.url_profile_temp))
        .crossFade()
        .thumbnail(sizeProfile)
        .bitmapTransform(CircleTransform(this))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imgProfile)
    tvUsername!!.text = ""
    imgProfile!!.setOnClickListener({
      if (mPref!!.getLoginStatus()) {
        startActivityForResult(Intent(this@MainActivity, ProfileActivity::class.java),
            PROFILE_INTENT)
      } else {
        startActivityForResult(Intent(this@MainActivity, LoginActivity::class.java), LOGIN_INTENT)
      }
    })
  }

  fun setUpUser() {
    if (!mPref!!.getLoginStatus()) return
    var user: User = mPref!!.getUser()
    loadUserDetails(user)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK) {
      when (requestCode) {
        LOGIN_INTENT -> {
          val user: User = mPref!!.getUser()
          loadUserDetails(user)
        }
        PROFILE_INTENT -> {
          setDefaultProfile()
        }
      }
    }
  }

  private fun startActivity(activity: Activity) {
    startActivity(Intent(this@MainActivity, activity::class.java))
    mDrawerLayout.closeDrawers()
  }

  override fun onBackPressed() {
    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
      mDrawerLayout.closeDrawers()
      return
    }
    if (mFlagOnBackPress && navItemIndex != TAG_NOW) {
      navItemIndex = TAG_NOW
      loadMovieFragment()
      return
    }
    super.onBackPressed()
  }

  fun loadUserDetails(user: User) {
    Glide.with(this).load(
        Constants.GRAVATAR_URL + user.avatar!!.gravatar!!.hash + Constants.GRAVATAR_SIZE)
        .crossFade()
        .thumbnail(sizeProfile)
        .bitmapTransform(CircleTransform(this))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imgProfile)
    if (!TextUtils.isEmpty(user.name)) {
      tvUsername!!.text = user.name
    } else {
      tvUsername!!.text = user.username
    }

  }
}
