package com.framgia.moviedbtraining.login

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.WindowManager
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : Activity(), LoginContract.ViewModel {
  private var mPresenter: LoginPresenter? = null

  override fun showLoading() {
    pbLoading.visibility = View.VISIBLE
  }

  override fun hideLoading() {
    pbLoading.visibility = View.GONE
  }

  override fun showSnack(message: String) {

  }

  override fun login(username: String, password: String) {

  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    edtUsername.addTextChangedListener(SimpleWatcher(edtUsername))
    edtPassword.addTextChangedListener(SimpleWatcher(edtPassword))
    btnLogin.setOnClickListener({ doLogin() })
    ivClose.setOnClickListener({ this.finish() })
    mPresenter = LoginPresenter(this)
  }

  private fun doLogin() {
    if (!validateName() || !validatePassword()) {
      return
    }
    mPresenter!!.doLogin(edtUsername!!.text.toString().trim(), edtPassword!!.text.toString())
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