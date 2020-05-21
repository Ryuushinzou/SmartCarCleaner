package com.scc.networking

import com.scc.networking.exceptions.HttpCallFailureException
import com.scc.networking.exceptions.NoNetworkException
import com.scc.networking.exceptions.ServerUnreachableException
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Observable<T>.subscribeWithNetworkErrors(
    consumer: (T) -> Unit,
    onError: (Throwable) -> Unit
): Disposable = this.subscribe(
    consumer,
    { error -> onError(filter(error)) }
)

private fun filter(error: Throwable): Throwable = when (error) {
    is SocketTimeoutException -> NoNetworkException(error)
    is UnknownHostException -> ServerUnreachableException(error)
    is HttpException -> HttpCallFailureException(error)
    else -> error
}