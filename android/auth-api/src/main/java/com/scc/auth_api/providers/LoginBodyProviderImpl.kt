package com.scc.auth_api.providers

import com.scc.auth_api.requests.LoginBody
import com.scc.networking.providers.RequestBodyProvider

/**
 * Implementation of the [LoginBodyProvider].
 *
 * @property username to be used for generating the [LoginBody]
 * @property password to be used for generating the [LoginBody]
 */
class LoginBodyProviderImpl(
    private var username: String? = null,
    private var password: String? = null
) : LoginBodyProvider {
    override fun withCredentials(username: String, password: String): RequestBodyProvider<LoginBody> {
        this.username = username
        this.password = password
        return this
    }

    override fun createBody(): LoginBody {
        if(this.username == null) {
            throw IllegalArgumentException("Username cannot be null")
        }

        if(this.password == null) {
            throw IllegalArgumentException("Password cannot be null")
        }

        return LoginBody(username!!, password!!)
    }
}