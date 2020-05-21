package com.scc.auth_api

/**
 * Factory used for building the [AuthApi].
 */
object Factory {
    /**
     * Public method to access the [AuthApi] by a consumer of the auth-api module.
     *
     * @return the abstracted implementation of the [AuthApi] to be used for
     * authentication with the remote server's web service.
     */
    fun createAuthApi(): AuthApi = AuthApiImpl()
}