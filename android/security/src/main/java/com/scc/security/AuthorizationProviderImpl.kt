package com.scc.security

import com.scc.security.exceptions.AuthorizationNotSetException

/**
 * Implementation of the [AuthorizationProvider].
 */
class AuthorizationProviderImpl: AuthorizationProvider {
    private var authorization: String? = null

    override fun setAuthorization(authorization: String) {
        this.authorization = authorization
    }

    override fun getAuthorization(): String {
        return authorization ?: throw AuthorizationNotSetException()
    }
}