package com.scc.networking.exceptions

import com.scc.networking.data.HttpStatus

class BadHttpRequestException(override val message: String = HttpStatus.STATUS_BAD_REQUEST.message) :
    Exception(message) {
    override fun toString(): String {
        return "Code: ${HttpStatus.STATUS_BAD_REQUEST} Message: $message"
    }
}