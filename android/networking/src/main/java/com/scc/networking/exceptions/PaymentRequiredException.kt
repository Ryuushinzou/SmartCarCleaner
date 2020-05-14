package com.scc.networking.exceptions

import com.scc.networking.data.HttpStatus

class PaymentRequiredException(override val message: String = HttpStatus.STATUS_PAYMENT_REQUIRED.message) :
    Exception(message) {
    override fun toString(): String {
        return "Code: ${HttpStatus.STATUS_PAYMENT_REQUIRED} Message: $message"
    }
}