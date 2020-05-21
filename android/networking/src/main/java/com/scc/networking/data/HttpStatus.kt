package com.scc.networking.data

/**
 * Supported HTTP status codes.
 */
enum class HttpStatus(val value: Int, val message: String) {
//    Success
    STATUS_OK(200, "SUCCESS"),
    STATUS_CREATED(201, "CREATED"),

//    Client error
    STATUS_BAD_REQUEST(400, "BAD REQUEST"),
    STATUS_UNAUTHORIZED(401, "UNAUTHORIZED"),
    STATUS_PAYMENT_REQUIRED(402, "PAYMENT REQUIRED"),
    STATUS_FORBIDDEN(403, "FORBIDDEN"),
    STATUS_NOT_FOUND(404, "NOT FOUND"),
    STATUS_REQUEST_TIMEOUT(408, "REQUEST TIMEOUT"),

//    Server error
    STATUS_SERVER_ERROR(500, "SERVER ERROR"),
    STATUS_BAD_GATEWAY(502, "BAD GATEWAY")
}