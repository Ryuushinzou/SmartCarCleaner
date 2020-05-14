package com.scc.networking.exceptions

import com.scc.networking.data.HttpStatus

class UnauthorizedHttpException(override val message: String = HttpStatus.STATUS_UNAUTHORIZED.message) :
    Exception(message) {
    override fun toString(): String {
        return "Code: ${HttpStatus.STATUS_UNAUTHORIZED} Message: $message"
    }
}