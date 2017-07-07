package com.framgia.moviedbtraining.search

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.framgia.moviedbtraining.R
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by FRAMGIA\pham.duc.nam on 05/07/2017.
 */

class SearchActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)
    setSupportActionBar(toolbar)
    supportActionBar!!.setTitle(getString(R.string.nav_search))
  }
}
