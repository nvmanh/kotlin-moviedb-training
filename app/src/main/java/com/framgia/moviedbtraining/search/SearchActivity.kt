package com.framgia.moviedbtraining.search

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.view.View
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.databinding.ActivitySearchBinding
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.utils.GeneralUtil
import com.framgia.moviedbtraining.utils.SimpleTextWatcher
import com.framgia.moviedbtraining.widget.EndlessRecyclerOnScrollListener

/**
 * Created by FRAMGIA\pham.duc.nam on 05/07/2017.
 */

class SearchActivity : AppCompatActivity(), SearchContract.ViewModel {
  lateinit var mPresenter: SearchPresenter
  private var mAdapter: SearchAdapter? = null
  private var mMovie: ArrayList<Movie> = arrayListOf()
  lateinit var mBinding: ActivitySearchBinding
  var mText: String = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_search)
    setSupportActionBar(mBinding.toolbar)
    supportActionBar!!.setTitle(getString(R.string.nav_search))
    getText()
  }

  private fun getText() {
    mBinding.layoutSearchResult.edtSearch.addTextChangedListener(
        SimpleWatcher(mBinding.layoutSearchResult.edtSearch))
  }

  private fun getDataSearch(text: String) {
    init(text)
  }

  fun init(text: String) {
    mPresenter = SearchPresenter(this)
    mPresenter.getResult(text)
    mPresenter.addEndlessListener()
    mAdapter = SearchAdapter(mMovie, R.layout.item_search_movie)
    mBinding.layoutSearchResult.mRecyclerViewResult.apply {
      val linearLayout = LinearLayoutManager(context)
      layoutManager = linearLayout
    }
    mBinding.layoutSearchResult.mRecyclerViewResult.adapter = mAdapter
  }

  fun onClickToInput(view: View) {
    mBinding.layoutSearch.lnSearch.visibility = View.GONE
    mBinding.layoutSearchResult.lnViewResult.visibility = View.VISIBLE
    mBinding.layoutSearchResult.edtSearch.requestFocus()
    GeneralUtil.showKeyboard(this, mBinding.layoutSearchResult.edtSearch)
  }

  fun onClickToSearch(view: View) {
    mMovie.clear()
    GeneralUtil.hiddenKeyboard(this, mBinding.layoutSearchResult.edtSearch)
    if (mText.isNullOrEmpty()) {
      mBinding.layoutSearch.lnSearch.visibility = View.VISIBLE
      mBinding.layoutSearchResult.lnViewResult.visibility = View.GONE
    } else {
      mMovie.clear()
      mBinding.layoutSearchResult.lnNoResult.visibility = View.GONE
      getDataSearch(mText)
    }
  }

  override fun showResult(movie: List<Movie>) {
    mMovie.addAll(movie)
    mAdapter!!.notifyDataSetChanged()
  }

  override fun showSnack(message: String) {
    if (mMovie.size != 0) {
      return
    }
    mBinding.layoutSearchResult.lnNoResult.visibility = View.VISIBLE
  }

  override fun addEndlessListener(listener: EndlessRecyclerOnScrollListener) {
    mBinding.layoutSearchResult.mRecyclerViewResult.addOnScrollListener(listener)
  }

  override fun hideLoading() {
    mBinding.layoutSearchResult.pbHeaderProgress.visibility = View.GONE
  }

  override fun showLoading() {
    mBinding.layoutSearchResult.pbHeaderProgress.visibility = View.VISIBLE
  }

  inner class SimpleWatcher(private val view: View) : SimpleTextWatcher() {
    override fun afterTextChanged(s: Editable?) {
      mText = s.toString()
      if (mText.isEmpty()) {
        mBinding.layoutSearchResult.tvRight.text = getString(R.string.cancel)
      } else {
        mBinding.layoutSearchResult.tvRight.text = getString(R.string.nav_search)
      }
    }
  }
}
