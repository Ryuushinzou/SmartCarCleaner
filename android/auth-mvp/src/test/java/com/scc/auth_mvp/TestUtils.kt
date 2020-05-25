package com.scc.auth_mvp

import com.scc.auth_api.requests.LoginBody
import com.scc.auth_api.requests.RegisterBody
import com.scc.auth_api.responses.RegisterResponse
import com.scc.networking.exceptions.UnauthorizedHttpException
import org.mockito.Mockito

internal const val TEST_VALID_USERNAME = "validUsername"
internal const val TEST_VALID_PASSWORD = "validPassword"
internal const val TEST_VALID_EMAIL = "test@test.com"
internal const val TEST_VALID_PHONE = "0760000000"

internal const val TEST_INVALID_USERNAME = "invalid-username"
internal const val TEST_INVALID_PASSWORD = "invalid-password"
internal const val TEST_INVALID_EMAIL = "test@test"
internal const val TEST_INVALID_PHONE = "076000000"

internal const val TEST_AUTHORIZATION_STRING = "test-authorization-string"

internal val TEST_INVALID_PASSWORD_EXCEPTION = UnauthorizedHttpException("Invalid password")
internal val TEST_INVALID_CREDENTIALS_EXCEPTION = UnauthorizedHttpException("Invalid credentials")

internal const val TEST_USER_TYPE = "User"

internal val TEST_REGISTER_SUCCESS_RESPONSE = RegisterResponse(
    TEST_VALID_USERNAME,
    TEST_VALID_EMAIL,
    TEST_VALID_PHONE,
    null,
    TEST_USER_TYPE
)

internal fun <T : Any> eq(value: T): T = Mockito.eq(value) ?: value

object Register {
    internal val TEST_VALID_REGISTER_BODY = RegisterBody(
        TEST_VALID_USERNAME,
        TEST_VALID_EMAIL,
        TEST_VALID_PHONE,
        TEST_VALID_PASSWORD
    )

    internal val TEST_INVALID_REGISTER_BODY = RegisterBody(
        TEST_INVALID_USERNAME,
        TEST_INVALID_EMAIL,
        TEST_INVALID_PHONE,
        TEST_INVALID_PASSWORD
    )
}

object Login {
    internal val TEST_VALID_LOGIN_BODY = LoginBody(TEST_VALID_USERNAME, TEST_VALID_PASSWORD)
    internal val TEST_INVALID_USERNAME_BODY = LoginBody(TEST_INVALID_USERNAME, TEST_INVALID_PASSWORD)
    internal val TEST_INVALID_PASSWORD_BODY = LoginBody(TEST_VALID_USERNAME, TEST_INVALID_PASSWORD)
}