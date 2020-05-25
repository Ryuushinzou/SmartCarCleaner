package com.scc.auth_mvp.register

/**
 * TODO - docs
 */
class RegisterContract {
    /**
     * TODO - docs
     */
    interface View: com.scc.mvp.BaseContract.View {
        /**
         * TODO - docs
         */
        fun showLoading()

        fun onRegisterSuccessful()
    }

    /**
     * TODO - docs
     */
    abstract class Presenter: com.scc.mvp.BaseContract.Presenter<View>() {
        /**
         * TODO - docs
         */
        abstract fun executeRegister(
            username: String? = null,
            email: String? = null,
            phone: String? = null,
            password: String
        )
    }
}