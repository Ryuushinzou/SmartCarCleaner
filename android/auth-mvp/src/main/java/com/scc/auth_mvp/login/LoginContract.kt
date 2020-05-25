package com.scc.auth_mvp.login

/**
 * Contract definition for the Login component.
 */
class LoginContract {
    /**
     * Blueprint of the Login view.
     */
    interface View: com.scc.mvp.BaseContract.View {
        /**
         * Update UI to disable interaction and display a loading view.
         */
        fun showLoading()

        /**
         * Update UI's state after successfully authenticating the user.
         */
        fun onLoginSuccessful()
    }

    /**
     * Blueprint of the Login presenter.
     */
    abstract class Presenter: com.scc.mvp.BaseContract.Presenter<View>() {
        /**
         * Start the authentication process for the user.
         *
         * @param username to be used by the request
         * @param password to be used by the request
         */
        abstract fun executeLogin(username: String, password: String)
    }
}