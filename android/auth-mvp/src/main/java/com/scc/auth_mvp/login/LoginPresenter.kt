package com.scc.auth_mvp.login

import androidx.annotation.VisibleForTesting
import com.scc.auth_api.AuthApi
import com.scc.auth_api.Factory
import com.scc.auth_api.providers.LoginBodyProvider
import com.scc.auth_api.providers.LoginBodyProviderImpl
import com.scc.networking.RetrofitInstance
import com.scc.networking.subscribeWithNetworkErrors
import com.scc.security.AuthorizationProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Implementation of the [LoginContract.Presenter].
 *
 * @property subscriptions - [CompositeDisposable] to store the registered requests
 * @property api - the [AuthApi] to be used to authenticate the user
 * @property view - the [LoginContract.View] to be attached to the presenter
 * @property subscribeOn - the [Scheduler] to be used for moving the async requests off the main thread
 * @property observeOn - the [Scheduler] to be used for updating the UI with the resulted data / states
 */
class LoginPresenter @VisibleForTesting constructor(
    private val subscriptions: CompositeDisposable,
    private val api: AuthApi = Factory.createAuthApi(),
    private val bodyProvider: LoginBodyProvider = LoginBodyProviderImpl(),
    private val authorizationManager: AuthorizationProvider,
    private val subscribeOn: Scheduler,
    private val observeOn: Scheduler
) : LoginContract.Presenter() {
    /**
     * Public constructor to be used by any consumers of the component. Passing a value to
     * the subscribeOn or observeOn parameters enables the consumer to manage the threading
     * of the interactions between the [LoginContract.View] & the [LoginContract.Presenter].
     *
     * If no values are passed for the subscribeOn or observeOn parameters, defaults will be
     * used, moving the subscriptions off the main thread.
     *
     * @param subscribeOn - the [Scheduler] to be used for moving the async requests off the main thread
     * @property observeOn - the [Scheduler] to be used for updating the UI with the resulted data / states
     */
    constructor(subscribeOn: Scheduler? = null, observeOn: Scheduler? = null) : this(
        subscriptions = CompositeDisposable(),
        api = Factory.createAuthApi(),
        authorizationManager = com.scc.security.Factory.createAuthorizationProvider(),
        subscribeOn = subscribeOn ?: Schedulers.io(),
        observeOn = observeOn ?: AndroidSchedulers.mainThread()
    )

    /**
     * Public constructor to be used by any consumers that need to use the [LoginPresenter]
     * without managing the threading of the interactions.
     */
    constructor() : this(null, null)

    override fun executeLogin(username: String, password: String) {
        view?.showLoading()

        val loginBody = try {
            bodyProvider.withCredentials(username, password)
                .createBody()
        } catch (e: IllegalArgumentException) {
            view?.showError(e)
            return
        }

        subscriptions.add(
            api.login(loginBody)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribeWithNetworkErrors(
                    { authorization -> onLoginSuccessful(authorization) },
                    { exception ->
                        val isHandled = handleHttpException(exception)
                        if (!isHandled) {
                            exception.printStackTrace()

                            view?.showError(RuntimeException("Unknown problem encountered"))
                        }
                    }
                )
        )
    }

    private fun onLoginSuccessful(authorization: String) {
        authorizationManager.setAuthorization(authorization)
        RetrofitInstance.setAuthorizationProvider(authorizationManager)
        view?.onLoginSuccessful()
    }
}