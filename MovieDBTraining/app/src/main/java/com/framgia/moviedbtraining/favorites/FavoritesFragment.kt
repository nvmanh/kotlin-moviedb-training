package com.framgia.moviedbtraining.favorites

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.moviedbtraining.R

/**
 * Created by FRAMGIA\pham.duc.nam on 05/07/2017.
 */

class FavoritesFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(R.layout.fragment_favorites, container, false)
  }
}
