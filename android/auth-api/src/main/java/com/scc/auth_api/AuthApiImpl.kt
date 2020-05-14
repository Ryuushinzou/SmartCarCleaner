package com.scc.auth_api

import com.scc.auth_api.requests.LoginBody
import com.scc.auth_api.services.AuthService
import com.scc.networking.RetrofitInstance
import io.reactivex.Observable

/**
 * Implementation of the [AuthApi].
 *
 * @property authService to be used for authenticating users against the server
 */
class AuthApiImpl(
    private val authService: AuthService = RetrofitInstance.getRetrofitInstance()
        .create(AuthService::class.java)
): AuthApi {
    override fun login(
        body: LoginBody
    ): Observable<String> {
        return authService.login(body.username, body.password)
    }
}