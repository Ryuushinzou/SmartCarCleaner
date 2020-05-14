package com.scc.logging

class Loggable(
    internal val tag: String? = null,
    internal var logLevel: LogLevel = LogLevel.VERBOSE,
    internal var message: String? = null,
    internal var throwable: Throwable? = null
) {
    fun v(message: String? = null, throwable: Throwable? = null) {
        this.logLevel = LogLevel.VERBOSE
        this.message = message
        this.throwable = throwable
        Logger.log(this)
    }

    fun i(message: String? = null, throwable: Throwable? = null) {
        this.logLevel = LogLevel.INFO
        this.message = message
        this.throwable = throwable
        Logger.log(this)
    }

    fun d(message: String? = null, throwable: Throwable? = null) {
        this.logLevel = LogLevel.DEBUG
        this.message = message
        this.throwable = throwable
        Logger.log(this)
    }

    fun e(message: String? = null, throwable: Throwable? = null) {
        this.logLevel = LogLevel.ERROR
        this.message = message
        this.throwable = throwable
        Logger.log(this)
    }
}