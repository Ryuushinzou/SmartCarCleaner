package com.scc.networking.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Http Interceptor used to add additional headers to the Http requests.
 *
 * @property extraHeaders   - the [Map] containing the key-value pairs representing the headers
 *                          to be added to the Http requests
 */
class HeadersInterceptor(private val extraHeaders: Map<String, String> = emptyMap()) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .also { requestBuilder ->
                    extraHeaders.forEach { header ->
                        requestBuilder.addHeader(
                            header.key,
                            header.value
                        )
                    }
                }
                .build()
        )
    }
}