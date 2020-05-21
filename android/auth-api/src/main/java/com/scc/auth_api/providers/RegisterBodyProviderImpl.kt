package com.scc.auth_api.providers

import com.scc.auth_api.requests.RegisterBody
import com.scc.networking.providers.RequestBodyProvider

class RegisterBodyProviderImpl(
    private var username: String? = null,
    private var email: String? = null,
    private var phone: String? = null,
    private var password: String? = null
) : RegisterBodyProvider {
    override fun with(
        username: String?,
        email: String?,
        phone: String?,
        password: String
    ): RequestBodyProvider<RegisterBody> {
        this.username = username
        this.email = email
        this.phone = phone
        this.password = password
        return this
    }

    override fun username(username: String?): RequestBodyProvider<RegisterBody> {
        this.username = username
        return this
    }

    override fun email(email: String?): RequestBodyProvider<RegisterBody> {
        this.email = email
        return this
    }

    override fun phone(phone: String?): RequestBodyProvider<RegisterBody> {
        this.phone
        return this
    }

    override fun password(password: String): RequestBodyProvider<RegisterBody> {
        this.password
        return this
    }

    override fun createBody(): RegisterBody {
        if (this.username == null) {
            throw IllegalArgumentException("Username cannot be null")
        }

        if (this.email == null) {
            throw IllegalArgumentException("Email cannot be null")
        }

        if (this.password == null) {
            throw IllegalArgumentException("Password cannot be null")
        }

        return RegisterBody(username!!, email!!, phone, password!!)
    }
}