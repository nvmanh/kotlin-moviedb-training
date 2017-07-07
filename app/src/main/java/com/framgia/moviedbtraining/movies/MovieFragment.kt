package com.framgia.moviedbtraining.movies

/**
 * Created by FRAMGIA\pham.duc.nam on 05/07/2017.
 */

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.framgia.moviedbtraining.MainActivity
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.utils.GeneralUtil
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(), MovieContractNew.ViewModel {

  private var mPresenter: MoviePresenter? = null
  private var mAdapter: MovieAdapter? = null
  private var mMovie: ArrayList<Movie> = arrayListOf()

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(R.layout.fragment_movie, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val activity = activity as MainActivity
    val mType = activity.getType()
    init(mType)
  }

  fun init(type: Int) {
    when (type) {
      Constants.TAG_NOW -> {
        mPresenter = MoviePresenter(Constants.TAB_NOW, this)
      }
      Constants.TAG_POPULAR -> {
        mPresenter = MoviePresenter(Constants.TAB_POPULAR, this)
      }
      Constants.TAG_TOP_RATED -> {
        mPresenter = MoviePresenter(Constants.TAB_RATED, this)
      }
      Constants.TAG_UPCOMING -> {
        mPresenter = MoviePresenter(Constants.TAB_UPCOMING, this)
      }
    }
    if (mPresenter == null) {
      return
    }
    mPresenter!!.getMovies()
    mPresenter!!.addEndlessListener()
    mAdapter = MovieAdapter(mMovie, R.layout.item_list_movies)
    rvMovies.layoutManager = GridLayoutManager(this.context,
        2) as RecyclerView.LayoutManager?
    rvMovies.adapter = mAdapter
  }

  override fun addEndlessListener(listener: EndlessRecyclerOnScrollListener) {
    rvMovies.addOnScrollListener(listener)
  }

  override fun showLoading() {
    pbHeaderProgress.visibility = View.VISIBLE
  }

  override fun hideLoading() {
    pbHeaderProgress.visibility = View.GONE
  }

  override fun showMovies(movie: List<Movie>) {
    mMovie.addAll(movie)
    mAdapter!!.notifyDataSetChanged()
  }

  override fun showSnack(message: String) {
    GeneralUtil.showSnackbar(rvMovies, message)
  }
}
