package com.scc.networking

import com.scc.networking.data.HttpStatus
import com.scc.networking.exceptions.*
import retrofit2.Response

/**
 * Base structure of a Retrofit API
 *
 * @throws
 */
abstract class AbstractApi {
    /**
     * Check the response code and throw the related exception if required.
     *
     * @throws BadHttpRequestException
     * @throws UnauthorizedHttpException
     * @throws PaymentRequiredException
     * @throws HttpRequestTimeoutException
     * @throws ServerException
     */
    fun validateResponse(response: Response<*>) {
        when(response.code()) {
            HttpStatus.STATUS_OK.value -> {
                //  do nothing
            }
            HttpStatus.STATUS_CREATED.value -> {
                //  do nothing
            }
            HttpStatus.STATUS_BAD_REQUEST.value -> {
                throw BadHttpRequestException()
            }
            HttpStatus.STATUS_UNAUTHORIZED.value -> {
                throw UnauthorizedHttpException()
            }
            HttpStatus.STATUS_PAYMENT_REQUIRED.value -> {
                throw PaymentRequiredException()
            }
            HttpStatus.STATUS_FORBIDDEN.value -> {
                //  do nothing
            }
            HttpStatus.STATUS_NOT_FOUND.value -> {
                //  do nothing
            }
            HttpStatus.STATUS_REQUEST_TIMEOUT.value -> {
                throw HttpRequestTimeoutException()
            }
            HttpStatus.STATUS_SERVER_ERROR.value -> {
                throw ServerException()
            }
            HttpStatus.STATUS_BAD_GATEWAY.value -> {
                //  do nothing
            }
        }
    }
}