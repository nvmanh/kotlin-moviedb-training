package com.framgia.moviedbtraining.login

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.WindowManager
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.constants.Constants
import com.framgia.moviedbtraining.model.User
import com.framgia.moviedbtraining.utils.GeneralUtil
import com.framgia.moviedbtraining.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : Activity(), LoginContract.ViewModel {
  override fun userData(user: User) {
    val returnIntent = intent
    setResult(Activity.RESULT_OK, returnIntent)
    finish()
  }

  private var mPresenter: LoginPresenter? = null

  override fun showLoading() {
    pbLoading.visibility = View.VISIBLE
  }

  override fun hideLoading() {
    pbLoading.visibility = View.GONE
  }

  override fun showSnack(message: String) {
    GeneralUtil.showSnackbar(pbLoading, message)
  }

  override fun login(username: String, password: String) {

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    init()
  }

  fun init() {
    edtUsername.addTextChangedListener(SimpleWatcher(edtUsername))
    edtPassword.addTextChangedListener(SimpleWatcher(edtPassword))
    btnLogin.setOnClickListener({ doLogin() })
    ivClose.setOnClickListener({ this.finish() })
    tvRegister.setOnClickListener({ openWebRegister() })
    mPresenter = LoginPresenter(this)
  }

  private fun doLogin() {
    if (!validateName() || !validatePassword()) {
      return
    }
    mPresenter!!.doLogin(edtUsername!!.text.toString().trim(), edtPassword!!.text.toString())
  }

  fun openWebRegister() {
    try {
      val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.REGISTER_URL))
      startActivity(browserIntent)
    } catch (e: ActivityNotFoundException) {
      GeneralUtil.showSnackbar(btnLogin, getString(R.string.err_web_browser))
      e.printStackTrace()
    }
  }

  private fun validateName(): Boolean {
    if (edtUsername!!.text.toString().trim().isEmpty()) {
      layoutUsername.error = getString(R.string.err_msg_username)
      requestFocus(edtUsername)
      return false
    } else {
      layoutUsername.isErrorEnabled = false
    }

    return true
  }

  private fun validatePassword(): Boolean {
    if (edtPassword.text.toString().length < Constants.MINIMUM_PASSWORD) {
      layoutPassword.error = getString(R.string.err_msg_min_password)
      requestFocus(edtPassword)
      return false
    }
    if (edtPassword!!.text.toString().trim().isEmpty()) {
      layoutPassword.error = getString(R.string.err_msg_password)
      requestFocus(edtPassword)
      return false
    } else {
      layoutPassword.isErrorEnabled = false
    }
    return true
  }

  private fun requestFocus(view: View) {
    if (view.requestFocus()) {
      window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
  }

  inner class SimpleWatcher(private val view: View) : SimpleTextWatcher() {
    override fun afterTextChanged(s: Editable?) {
      when (view.id) {
        R.id.edtUsername -> validateName()
        R.id.edtPassword -> validatePassword()
      }
    }
  }
}