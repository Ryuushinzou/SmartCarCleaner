package com.scc.auth_ui

import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.ViewById

@EFragment(resName = "f_login_layout")
class LoginFragment: Fragment() {
    @ViewById(resName = "login_username_container")
    lateinit var usernameContainer: TextInputLayout
    @ViewById(resName = "login_username_input")
    lateinit var usernameInput: TextInputEditText
    @ViewById(resName = "login_password_container")
    lateinit var passwordContainer: TextInputLayout
    @ViewById(resName = "login_password_input")
    lateinit var passwordInput: TextInputEditText
    @ViewById(resName = "login_submit_button")
    lateinit var loginButton: Button

//    @AfterViews
////    fun setupViews() {
////        presenter.attach(this)
////    }

//    @Click(resName = "login_submit_button")
//    fun onLoginClicked() {
//        val username = usernameInput.text.toString()
//        val password = passwordInput.text.toString()
//
//        if(username.isEmpty() || password.isEmpty()) {
//            return
//        }
//
//        presenter.executeLogin(username, password)
//    }
}