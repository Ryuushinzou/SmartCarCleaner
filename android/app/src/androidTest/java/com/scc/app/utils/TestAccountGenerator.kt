package com.scc.app.utils

import java.sql.Timestamp

class TestAccountGenerator(
    private val username: String = "test",
    private val domain: String = "internal",
    private val extension: String = "com"
) {
    fun generateUsername(): String {
        val timestamp = Timestamp(System.currentTimeMillis()/1000)
        return "${username}_${timestamp.replaceSpaces()}"
    }

    fun generateTestEmail(): String {
        val timestamp = Timestamp(System.currentTimeMillis()/1000)
        return "${username}_${timestamp.replaceSpaces()}@${domain}.$extension"
    }
}

fun Timestamp.replaceSpaces(): String = toString()
    .replace(' ', '_')
    .replace(':', '_')