package com.scc.auth_mvp.exceptions

import java.lang.RuntimeException

open class NetworkException(error: Throwable): RuntimeException(error)

class NoNetworkApiException(error: Throwable): NetworkException(error)

class ServerUnreachableApiException(error: Throwable): NetworkException(error)

class HttpCallFailureApiException(error: Throwable): NetworkException(error)