package com.scc.networking.exceptions

import com.scc.networking.data.HttpStatus

class ServerException(override val message: String = HttpStatus.STATUS_SERVER_ERROR.message): Exception(message) {
    override fun toString(): String {
        return "Code: ${HttpStatus.STATUS_SERVER_ERROR} Message: $message"
    }
}