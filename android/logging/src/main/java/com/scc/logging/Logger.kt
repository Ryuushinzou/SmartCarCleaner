package com.scc.logging

/**
 * TODO: Document classes
 */
object Logger {
    private var tag: String? = null
    private var logLevel: LogLevel = LogLevel.VERBOSE
    private var message: String? = null
    private var throwable: Throwable? = null

    fun tag(tag: String): Loggable {
        this.tag = tag
        return Loggable(tag)
    }

    fun v(message: String? = null, throwable: Throwable? = null) {
        this.logLevel = LogLevel.VERBOSE
        this.message = message
        this.throwable = throwable
    }

    fun i(message: String? = null, throwable: Throwable? = null) {
        logLevel = LogLevel.INFO
        this.message = message
        this.throwable = throwable
    }

    fun d(message: String? = null, throwable: Throwable? = null) {
        logLevel = LogLevel.DEBUG
        this.message = message
        this.throwable = throwable
    }

    fun e(message: String? = null, throwable: Throwable? = null) {
        logLevel = LogLevel.ERROR
        this.message = message
        this.throwable = throwable
    }

    internal fun log(loggable: Loggable) {
        //  TODO: Improve logging mechanism?
        println("${loggable.logLevel.name}: [ ${loggable.tag} ]: ${loggable.message}")
        loggable.throwable?.printStackTrace()
    }
}