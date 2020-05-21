package com.scc.security.exceptions

import com.scc.security.AuthorizationProvider

class AuthorizationNotSetException: Exception("Authorization string has not been provided prior to requesting it from the ${AuthorizationProvider::class.java.simpleName}")