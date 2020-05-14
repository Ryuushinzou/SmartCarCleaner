package com.scc.security

/**
 * Blueprint for the Authorization string manager.
 */
interface AuthorizationProvider {
    /**
     * Update the authorization string stored for authenticating future requests.
     *
     * @param authorization to be stored for further request authentication
     */
    fun setAuthorization(authorization: String)

    /**
     * Method used to access the authorization string to be used for authenticating requests.
     *
     * @return the authorization token provided by the server
     *
     * @throws [AuthorizationNotSetException] if the authorization string has not been
     * provided prior to requesting it.
     */
    fun getAuthorization(): String
}