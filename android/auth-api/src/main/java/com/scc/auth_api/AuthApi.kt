package com.scc.auth_api

import com.scc.auth_api.requests.LoginBody
import io.reactivex.Observable

/**
 * Blueprint of the Auth api.
 */
interface AuthApi {
    /**
     * Authenticate the user using the provided credentials.
     *
     * @param body to be used for authentication
     */
    fun login(body: LoginBody): Observable<String>
}