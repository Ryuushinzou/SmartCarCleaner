package com.scc.auth_api.providers

import com.scc.auth_api.requests.LoginBody
import com.scc.networking.providers.RequestBodyProvider

/**
 * The [RequestBodyProvider] used to generate [LoginBody] instances.
 */
interface LoginBodyProvider: RequestBodyProvider<LoginBody> {
    /**
     * Method to be used for setting additional parameters in the provider.
     *
     * @param username to be used by the provider
     * @param password to be used by the provider
     * @return the [LoginBodyProvider] instance
     */
    fun withCredentials(username: String, password: String): RequestBodyProvider<LoginBody>
}