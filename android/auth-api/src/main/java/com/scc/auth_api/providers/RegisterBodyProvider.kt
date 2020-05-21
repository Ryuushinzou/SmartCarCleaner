package com.scc.auth_api.providers

import com.scc.auth_api.requests.RegisterBody
import com.scc.networking.providers.RequestBodyProvider

/**
 * TODO - docs
 */
interface RegisterBodyProvider : RequestBodyProvider<RegisterBody> {
    /**
     * TODO - docs
     */
    fun with(
        username: String?,
        email: String?,
        phone: String?,
        password: String
    ): RequestBodyProvider<RegisterBody>

    /**
     * TODO - docs
     */
    fun username(username: String?): RequestBodyProvider<RegisterBody>

    /**
     * TODO - docs
     */
    fun email(email: String?): RequestBodyProvider<RegisterBody>

    /**
     * TODO - docs
     */
    fun phone(phone: String?): RequestBodyProvider<RegisterBody>

    /**
     * TODO - docs
     */
    fun password(password: String): RequestBodyProvider<RegisterBody>
}