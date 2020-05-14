package com.scc.security

/**
 * A factory acting like a Singleton holder for the [AuthorizationProvider] instance to be used
 * by all the consumer components.
 */
object Factory {
    /**
     * The instance of [AuthorizationProvider] to be used.
     */
    private var authorizationProvider: AuthorizationProvider? = null

    /**
     * Provide a single instance of the [AuthorizationProvider] for managing the authorization token.
     */
    fun createAuthorizationProvider(): AuthorizationProvider = authorizationProvider ?: run {
        synchronized(this) {
            authorizationProvider = AuthorizationProviderImpl()

            return@run authorizationProvider as AuthorizationProvider
        }
    }
}