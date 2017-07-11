package com.framgia.moviedbtraining.rest

import com.framgia.moviedbtraining.App
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.login.LoginContract
import com.framgia.moviedbtraining.model.Movie
import com.framgia.moviedbtraining.model.MoviesResponse
import com.framgia.moviedbtraining.model.ServiceResponse
import com.framgia.moviedbtraining.constants.Keys
import com.framgia.moviedbtraining.usermovies.UserMoviesContract
import com.framgia.moviedbtraining.model.User
import com.framgia.moviedbtraining.movies.MovieContractNew
import com.framgia.moviedbtraining.utils.ApplicationPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by FRAMGIA\babatunde.fatoye.sunday on 7/6/17.
 */
object RequestHelper {
  val mPref: ApplicationPrefs = ApplicationPrefs()

  fun getRequesMoviesByType(type: String, page: Int,
      mViewModel: MovieContractNew.ViewModel): List<Movie>? {
    mViewModel.showLoading()
    var movieList: List<Movie>? = arrayListOf()
    val apiService = ApiClient.client.create(ApiInterface::class.java)
    val call = apiService.getMovies(type, Constants.API_KEY, page)
    call.enqueue(object : Callback<MoviesResponse> {
      override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
        if (response.isSuccessful) {
          mViewModel.hideLoading()
          movieList = response.body()!!.results
          if (movieList!!.isEmpty()) {
            return
          }
          mViewModel.showMovies(movieList!!)
        } else {
          mViewModel.hideLoading()
          mViewModel.showSnack(response.message().toString())
        }
      }

      override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
        mViewModel.hideLoading()
        mViewModel.showSnack(t.message.toString())
      }
    })
    return movieList
  }

  fun getToken(viewModel: LoginContract.ViewModel, username: String, password: String) {
    viewModel.showLoading()
    val apiService = ApiClient.client.create(ApiInterface::class.java)
    val call = apiService.getToken(Constants.API_KEY)
    call.enqueue(object : Callback<ServiceResponse> {
      override fun onResponse(call: Call<ServiceResponse>, response: Response<ServiceResponse>) {
        if (response.isSuccessful) {
          viewModel.hideLoading()
          var token = response.body()!!.token
          authLogin(viewModel, username, password, token)
        }
      }

      override fun onFailure(call: Call<ServiceResponse>, t: Throwable) {
        viewModel.hideLoading()
        viewModel.showSnack(t.message.toString())
      }
    })
  }

  fun authLogin(viewModel: LoginContract.ViewModel, username: String, password: String,
      token: String) {
    viewModel.showLoading()
    val apiService = ApiClient.client.create(ApiInterface::class.java)
    val call = apiService.authWithLogin(Constants.API_KEY, username, password, token)
    call.enqueue(object : Callback<ServiceResponse> {
      override fun onResponse(call: Call<ServiceResponse>, response: Response<ServiceResponse>) {
        viewModel.hideLoading()
        if (response.isSuccessful) {
          getSessionId(viewModel, token)
        } else {
          viewModel.showSnack(App.self().getString(R.string.err_msg_login_failed))
        }
      }

      override fun onFailure(call: Call<ServiceResponse>, t: Throwable) {
        viewModel.hideLoading()
        viewModel.showSnack(t.message.toString())
      }
    })
  }

  fun getSessionId(viewModel: LoginContract.ViewModel, token: String) {
    viewModel.showLoading()
    val apiService = ApiClient.client.create(ApiInterface::class.java)
    val call = apiService.getSessionId(Constants.API_KEY, token)
    call.enqueue(object : Callback<ServiceResponse> {
      override fun onResponse(call: Call<ServiceResponse>, response: Response<ServiceResponse>) {
        viewModel.hideLoading()
        if (response.isSuccessful) {
          val sessionId = response.body()!!.session
          mPref.setSessionId(sessionId)
          getUser(viewModel, sessionId)
        }
      }

      override fun onFailure(call: Call<ServiceResponse>, t: Throwable) {
        viewModel.hideLoading()
        viewModel.showSnack(t.message.toString())
      }
    })
  }

  fun getUser(viewModel: LoginContract.ViewModel, sessionId: String) {
    viewModel.showLoading()
    val apiService = ApiClient.client.create(ApiInterface::class.java)
    val call = apiService.getAccount(Constants.API_KEY, sessionId)
    call.enqueue(object : Callback<User> {
      override fun onResponse(call: Call<User>, response: Response<User>) {
        viewModel.hideLoading()
        if (response.isSuccessful) {
          val user = response.body()
          mPref.setUser(user!!)
          viewModel.userData(user)
        }
      }

      override fun onFailure(call: Call<User>, t: Throwable) {
        viewModel.hideLoading()
        viewModel.showSnack(t.message.toString())
      }
    })
  }

  fun getUserMovies(page: Int, type: String, mViewModel: UserMoviesContract.ViewModel): List<Movie>? {
    val user: User = mPref.getUser()
    val sessionId = mPref.getPrefData(Keys.SESSION_ID)
    var call:Call<MoviesResponse>?=null
    mViewModel.showLoading()
    var movieList: List<Movie>? = arrayListOf()
    val apiService = ApiClient.client.create(ApiInterface::class.java)
    when(type) {
      Constants.FAVOURITE_INTENT -> {
        call = apiService.getFavourites(Constants.API_KEY, sessionId, user.language!!, page)
      }
      Constants.RATING_INTENT -> {
        call = apiService.getRated(Constants.API_KEY, sessionId, user.language!!, page)
      }
    }
    call!!.enqueue(object : Callback<MoviesResponse> {
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