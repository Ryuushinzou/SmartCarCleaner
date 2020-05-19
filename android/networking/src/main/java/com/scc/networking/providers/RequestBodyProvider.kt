package com.scc.networking.providers

/**
 * Interface used for defining the providers for generic request bodies.
 */
interface RequestBodyProvider<out T> {
    /**
     * Method to be used for generating a request body.
     *
     * @return the request body to be used by the HTTP request call
     *
     * @throws IllegalArgumentException if the [RequestBodyProvider] does not have the required
     * data to create a new instance of [T]
     */
    fun createBody(): T
}