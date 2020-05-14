package com.scc.networking.callbacks

/**
 * Callback used for notifying completion of async requests.
 */
interface ApiCallback<T> {
    fun onResponse(response: T)
    fun onFailure(throwable: Throwable)
}