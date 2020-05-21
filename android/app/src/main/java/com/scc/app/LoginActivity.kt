package com.scc.app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.scc.auth_mvp.LoginContract
import com.scc.auth_mvp.LoginPresenter
import com.scc.auth_mvp.exceptions.HttpCallFailureApiException
import com.scc.auth_mvp.exceptions.NoNetworkApiException
import com.scc.auth_mvp.exceptions.ServerUnreachableApiException

class LoginActivity : AppCompatActivity(), LoginContract.View, TextWatcher {
    private lateinit var usernameContainer: TextInputLayout
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var loading: ViewGroup

    private val presenter: LoginContract.Presenter = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameContainer = findViewById(R.id.login_username_container)
        username = findViewById(R.id.login_username_input)
        password = findViewById(R.id.login_password_input)
        loginBtn = findViewById(R.id.login_submit_button)
        loading = findViewById(R.id.login_loading_container)

        username.addTextChangedListener(this@LoginActivity)
        password.addTextChangedListener(this@LoginActivity)

        loginBtn.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                return@setOnClickListener
            }

            presenter.executeLogin(username, password)
        }

        presenter.attach(this)
    }

    override fun onStop() {
        presenter.unsubscribe()
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun showError(error: Throwable) {
        hideLoading()

        Log.d(LoginActivity::class.java.simpleName, "Login failed!")
        error.printStackTrace()

        usernameContainer.isErrorEnabled = true
        usernameContainer.error = getUserError(error)
    }

    override fun onLoginSuccessful() {
        hideLoading()

        Log.d(LoginActivity::class.java.simpleName, "Login success!")
        navigateToMain()
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
    }

    private fun clearErrors() {
        usernameContainer.error = null
        usernameContainer.isErrorEnabled = false
    }

    private fun navigateToMain() = startActivity(
        Intent(
            this,
            MainActivity::class.java
        ).apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) })

    private fun getUserError(throwable: Throwable): String = when (throwable) {
        is NoNetworkApiException -> "No internet connection"
        is ServerUnreachableApiException -> "Could not connect to server"
        is HttpCallFailureApiException -> "Invalid username and password"
        else -> throwable.message ?: "Unknown error. Please try again later"
    }

    override fun afterTextChanged(p0: Editable?) {
        clearErrors()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //  do nothing
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //  do nothing
    }
}
