package com.scc.networking.exceptions

import com.scc.networking.data.HttpStatus

class HttpRequestTimeoutException(override val message: String = HttpStatus.STATUS_REQUEST_TIMEOUT.message) :
    Exception(message) {
    override fun toString(): String {
        return "Code: ${HttpStatus.STATUS_REQUEST_TIMEOUT.value} Message: $message"
    }
}