package com.scc.networking.interceptors

import com.scc.security.AuthorizationProvider
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Http Interceptor used to add the authorization string to all
 * the requests requiring authorization.
 *
 * @property authorizationProvider to be used to retrieve the authorization string
 */
class AuthorizationInterceptor(
    private val authorizationProvider: AuthorizationProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request().newBuilder()
                .addHeader("authorization", authorizationProvider.getAuthorization())
                .build()
        )
    }
}