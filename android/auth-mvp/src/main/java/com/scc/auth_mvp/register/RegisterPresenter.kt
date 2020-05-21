package com.scc.auth_mvp.register

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.scc.auth_api.AuthApi
import com.scc.auth_api.Factory
import com.scc.auth_api.providers.RegisterBodyProvider
import com.scc.auth_api.providers.RegisterBodyProviderImpl
import com.scc.auth_api.responses.RegisterResponse
import com.scc.networking.subscribeWithNetworkErrors
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * TODO - docs
 */
class RegisterPresenter @VisibleForTesting constructor(
    private val subscriptions: CompositeDisposable,
    private val api: AuthApi = Factory.createAuthApi(),
    private val bodyProvider: RegisterBodyProvider = RegisterBodyProviderImpl(),
    private val subscribeOn: Scheduler,
    private val observeOn: Scheduler
) : RegisterContract.Presenter() {
    /**
     * TODO - docs
     */
    constructor(subscribeOn: Scheduler? = null, observeOn: Scheduler? = null) : this(
        subscriptions = CompositeDisposable(),
        api = Factory.createAuthApi(),
        subscribeOn = subscribeOn ?: Schedulers.io(),
        observeOn = observeOn ?: AndroidSchedulers.mainThread()
    )

    /**
     * Public constructor to be used by any consumers that need to use the [RegisterPresenter]
     * without managing the threading of the interactions.
     */
    constructor() : this(null, null)

    override fun executeRegister(
        username: String?,
        email: String?,
        phone: String?,
        password: String
    ) {
        view?.showLoading()

        val registerBody = try {
            bodyProvider.with(username, email, phone, password)
                .createBody()
        } catch (e: IllegalArgumentException) {
            view?.showError(e)
            return
        }

        subscriptions.add(
            api.register(registerBody)
                .subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .subscribeWithNetworkErrors(
                    { registerResponse -> onRegisterSuccessful(registerResponse) },
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

    private fun onRegisterSuccessful(response: RegisterResponse) {
        view?.onRegisterSuccessful()
    }
}