package com.framgia.moviedbtraining

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.framgia.moviedbtraining.favorites.FavoritesFragment
import com.framgia.moviedbtraining.movies.MovieFragment
import com.framgia.moviedbtraining.rated.RatedFragment
import com.framgia.moviedbtraining.search.SearchActivity
import com.framgia.moviedbtraining.utils.CircleTransform
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main.*

class MainActivity : AppCompatActivity() {

  private val TAG_MOVIE: Int = 0
  private val TAG_FAVOR: Int = 1
  private val TAG_RATED: Int = 2

  private var activityTitles: Array<String>? = null
  var navItemIndex = TAG_MOVIE
  private var mHandler: Handler? = null
  private var imgProfile: ImageView? = null
  private val mFlagOnBackPress = true
  private val sizeProfile: Float = 0.5f

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    setDataProfile()
    setUpNavigationView()
    activityTitles = resources.getStringArray(R.array.nav_item_activity_titles)
    mHandler = Handler()
    if (savedInstanceState == null) {
      navItemIndex = TAG_MOVIE
      loadMovieFragment()
    }
  }

  private fun loadMovieFragment() {
    navi_view.getMenu().getItem(navItemIndex).setChecked(true)
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
    drawer_layout.closeDrawers()
    invalidateOptionsMenu()
  }

  private fun getMoviesFragment(): Fragment {
    when (navItemIndex) {
      TAG_MOVIE -> {
        return MovieFragment()
      }
      TAG_FAVOR -> {
        return FavoritesFragment()
      }
      TAG_RATED -> {
        return RatedFragment()
      }
      else -> return MovieFragment()
    }
  }

  private fun setUpNavigationView() {
    navi_view.setNavigationItemSelectedListener(
        NavigationView.OnNavigationItemSelectedListener { menuItem ->
          when (menuItem.itemId) {
            R.id.nav_movies -> {
              navItemIndex = TAG_MOVIE
            }
            R.id.nav_search -> {
              startActivity(Intent(this@MainActivity, SearchActivity::class.java))
              drawer_layout.closeDrawers()
              return@OnNavigationItemSelectedListener true
            }
            R.id.nav_favo -> {
              navItemIndex = TAG_FAVOR
            }
            R.id.nav_rate -> {
              navItemIndex = TAG_RATED
            }
            else -> navItemIndex = TAG_MOVIE
          }
          menuItem.isChecked = !menuItem.isChecked
          loadMovieFragment()
          true
        })

    val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar,
        R.string.openDrawer, R.string.closeDrawer) {

      override fun onDrawerClosed(drawerView: View?) {
        super.onDrawerClosed(drawerView)
      }

      override fun onDrawerOpened(drawerView: View?) {
        super.onDrawerOpened(drawerView)
      }
    }
    drawer_layout.addDrawerListener(actionBarDrawerToggle)
    actionBarDrawerToggle.syncState()
  }

  private fun setDataProfile() {
    imgProfile = navi_view.getHeaderView(0).findViewById(R.id.img_profile) as ImageView
    Glide.with(this).load(getString(R.string.url_profile_temp))
        .crossFade()
        .thumbnail(sizeProfile)
        .bitmapTransform(CircleTransform(this))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imgProfile)
  }

  override fun onBackPressed() {
    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
      drawer_layout.closeDrawers()
      return
    }
    if (mFlagOnBackPress && navItemIndex != TAG_MOVIE) {
      navItemIndex = TAG_MOVIE
      loadMovieFragment()
      return
    }
    super.onBackPressed()
  }
}
