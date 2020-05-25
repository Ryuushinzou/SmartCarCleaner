package com.scc.app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.scc.auth_mvp.login.LoginContract
import com.scc.auth_mvp.register.RegisterContract
import com.scc.auth_mvp.register.RegisterPresenter

class RegisterActivity : AppCompatActivity(), RegisterContract.View, LoginContract.View {
    private lateinit var usernameContainer: TextInputLayout
    private lateinit var username: EditText
    private lateinit var emailContainer: TextInputLayout
    private lateinit var email: EditText
    private lateinit var phoneContainer: TextInputLayout
    private lateinit var phone: EditText
    private lateinit var passwordContainer: TextInputLayout
    private lateinit var password: EditText
    private lateinit var confirmPasswordContainer: TextInputLayout
    private lateinit var confirmPassword: EditText
    private lateinit var registerButton: Button

    private lateinit var loading: ViewGroup

    private val registerPresenter: RegisterContract.Presenter = RegisterPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameContainer = findViewById(R.id.register_username_container)
        username = findViewById(R.id.register_username_input)
        emailContainer = findViewById(R.id.register_email_container)
        email = findViewById(R.id.register_email_input)
        phoneContainer = findViewById(R.id.register_phone_container)
        phone = findViewById(R.id.register_phone_input)
        passwordContainer = findViewById(R.id.register_password_container)
        password = findViewById(R.id.register_password_input)
        confirmPasswordContainer = findViewById(R.id.register_confirm_password_container)
        confirmPassword = findViewById(R.id.register_confirm_password_input)

        registerButton = findViewById(R.id.register_submit_button)

        loading = findViewById(R.id.register_loading_container)

        confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(newText: Editable?) {
                if (newText != password.text) {
                    confirmPasswordContainer.isErrorEnabled = true
                    confirmPasswordContainer.error = "Passwords do not match"
                } else {
                    confirmPasswordContainer.isErrorEnabled = false
                    confirmPasswordContainer.error = null
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //  do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //  do nothing
            }
        })

        registerButton.setOnClickListener {
            clearErrors()

            registerPresenter.executeRegister(
                username.text.toString(),
                email.text.toString(),
                phone.text.toString(),
                password.text.toString()
            )
        }

        registerPresenter.attach(this)
    }

    override fun onStop() {
        registerPresenter.unsubscribe()
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        registerPresenter.subscribe()
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun onLoginSuccessful() {
        hideLoading()
        navigateToMain()
    }

    override fun onRegisterSuccessful() {
        val username = username.text.toString()
        val password = password.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            return
        }

        navigateToLogin()
    }

    override fun showError(error: Throwable) {
//        TODO("Not yet implemented")
    }

    private fun navigateToMain() = startActivity(
        Intent(
            this,
            MainActivity::class.java
        ).apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) })

    private fun navigateToLogin() = startActivity(
        Intent(
            this,
            LoginActivity::class.java
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra(KEY_USERNAME_EXTRA, username.text.toString())
            putExtra(KEY_PASSWORD_EXTRA, password.text.toString())
        }
    )

    private fun hideLoading() {
        loading.visibility = View.GONE
    }

    private fun clearErrors() {
        usernameContainer.isErrorEnabled = false
        usernameContainer.error = null

        emailContainer.isErrorEnabled = false
        emailContainer.error = null

        confirmPasswordContainer.isErrorEnabled = false
        confirmPasswordContainer.error = null
    }
}
