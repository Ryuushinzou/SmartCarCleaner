package com.scc.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.scc.auth_mvp.LoginContract
import com.scc.auth_mvp.LoginPresenter

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button

    private val presenter: LoginContract.Presenter = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.login_username_input)
        password = findViewById(R.id.login_password_input)
        loginBtn = findViewById(R.id.login_submit_button)

        loginBtn.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()

            if(username.isEmpty() || password.isEmpty()) {
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

    override fun togglePasswordVisibility(isVisible: Boolean) {
        //  TODO("Not yet implemented")
    }

    override fun showLoading() {
        //  TODO("Not yet implemented")
    }

    override fun showError(error: Throwable) {
        Log.d(LoginActivity::class.java.simpleName, "Login failed!")
        error.printStackTrace()
    }

    override fun onLoginSuccessful() {
        Log.d(LoginActivity::class.java.simpleName, "Login success!")
    }
}
