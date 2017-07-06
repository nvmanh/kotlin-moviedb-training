package com.framgia.moviedbtraining.rest

import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.model.MoviesResponse
import com.framgia.moviedbtraining.movies.NowPlayingContractNew
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by FRAMGIA\babatunde.fatoye.sunday on 7/6/17.
 */
object RequestHelper {

  fun getNowPlaying(page: Int, mViewModel: NowPlayingContractNew.ViewModel): List<Movie>? {
    mViewModel.showLoading()
    var movieList: List<Movie>? = arrayListOf()
    val apiService = ApiClient.client.create(ApiInterface::class.java)
    val call = apiService.getNowPlaying(Constants.API_KEY, page)
    call.enqueue(object : Callback<MoviesResponse> {
      override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
        if (response.isSuccessful) {
          mViewModel.hideLoading()
          movieList = response.body()!!.results
          if (movieList!!.isEmpty()) {
            return
          }
          mViewModel.showMovies(movieList!!)
        }
      }

      override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
        mViewModel.hideLoading()
        mViewModel.showSnack(t.message.toString())
      }
    })
    return movieList
  }
}