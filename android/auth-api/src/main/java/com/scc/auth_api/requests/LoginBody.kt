package com.scc.auth_api.requests

/**
 * Data class to be used for building Login requests.
 *
 * @property username to be used for credentials authentication
 * @property password to be used for credentials authentication
 */
data class LoginBody(
    val username: String,
    val password: String
)