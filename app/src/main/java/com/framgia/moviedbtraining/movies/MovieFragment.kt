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
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.utils.GeneralUtil
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment(), NowPlayingContractNew.ViewModel {
  private var mPresenter: NowPlayingPresenter? = null
  private var mAdapter: NowPlayingAdapter? = null
  private var mMovie: ArrayList<Movie> = arrayListOf()

  fun NowPlayingFragment() {}

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  private var mType: Int = 0

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(R.layout.fragment_movie, container, false)
  }

  fun newinstance(type: Int): MovieFragment {
    val movieFragment = MovieFragment()
    mType = type
    return movieFragment
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    init()
  }

  fun init() {
    mPresenter = NowPlayingPresenter(this)
    mPresenter!!.getMovies()
    mPresenter!!.addEndlessListener()
    mAdapter = NowPlayingAdapter(mMovie!!, R.layout.item_nowplaying)
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
