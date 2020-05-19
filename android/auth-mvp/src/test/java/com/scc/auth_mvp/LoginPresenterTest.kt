package com.scc.auth_mvp

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.scc.auth_api.AuthApi
import com.scc.auth_api.providers.LoginBodyProvider
import com.scc.auth_api.requests.LoginBody
import com.scc.networking.exceptions.UnauthorizedHttpException
import com.scc.security.AuthorizationProvider
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

private const val TEST_VALID_USERNAME = "validUsername"
private const val TEST_INVALID_USERNAME = "invalid-username"
private const val TEST_VALID_PASSWORD = "validPassword"
private const val TEST_INVALID_PASSWORD = "invalid-password"

private val TEST_VALID_LOGIN_BODY = LoginBody(TEST_VALID_USERNAME, TEST_VALID_PASSWORD)
private val TEST_INVALID_USERNAME_BODY = LoginBody(TEST_INVALID_USERNAME, TEST_INVALID_PASSWORD)
private val TEST_INVALID_PASSWORD_BODY = LoginBody(TEST_VALID_USERNAME, TEST_INVALID_PASSWORD)

private const val TEST_AUTHORIZATION_STRING = "test-authorization-string"
private val TEST_INVALID_PASSWORD_EXCEPTION = UnauthorizedHttpException("Invalid password")
private val TEST_INVALID_CREDENTIALS_EXCEPTION = UnauthorizedHttpException("Invalid credentials")

private fun <T : Any> eq(value: T): T = Mockito.eq(value) ?: value

class LoginPresenterTest {

    private val testSubscriptions = CompositeDisposable()
    private val mockApi: AuthApi = mock {
        on { login(eq(TEST_VALID_LOGIN_BODY)) } doReturn Observable.just(
            TEST_AUTHORIZATION_STRING
        )
        on { login(eq(TEST_INVALID_PASSWORD_BODY)) } doReturn Observable.error(
            TEST_INVALID_PASSWORD_EXCEPTION
        )
        on { login(eq(TEST_INVALID_USERNAME_BODY)) } doReturn Observable.error(
            TEST_INVALID_CREDENTIALS_EXCEPTION
        )
    }
    private val mockLoginBodyProvider: LoginBodyProvider = mock {

    }
    private val mockAuthorizationManager: AuthorizationProvider = mock { }
    private val testScheduler: Scheduler = Schedulers.trampoline()

    private val sut = LoginPresenter(
        testSubscriptions,
        mockApi,
        mockLoginBodyProvider,
        mockAuthorizationManager,
        testScheduler,
        testScheduler
    )

    @Before
    fun setup() {
        `when`(
            mockLoginBodyProvider.withCredentials(
                com.nhaarman.mockitokotlin2.any(),
                com.nhaarman.mockitokotlin2.any()
            )
        ).thenReturn(mockLoginBodyProvider)
    }

    @Test
    fun `should set authorization for valid provided credentials (successful request)`() {
        //  Generate valid request body
        `when`(mockLoginBodyProvider.createBody()).thenReturn(TEST_VALID_LOGIN_BODY)

        //  Execute call to sut
        sut.executeLogin(TEST_VALID_USERNAME, TEST_VALID_PASSWORD)

        verify(mockLoginBodyProvider, times(1)).withCredentials(TEST_VALID_USERNAME, TEST_VALID_PASSWORD)
        verify(mockLoginBodyProvider, times(1)).createBody()
        verify(mockApi, times(1)).login(TEST_VALID_LOGIN_BODY)
        verify(mockAuthorizationManager, times(1)).setAuthorization(TEST_AUTHORIZATION_STRING)
    }

    @Test
    fun `should not set authorization for invalid password (failed request)`() {
        //  Generate invalid request body
        `when`(mockLoginBodyProvider.createBody()).thenReturn(TEST_INVALID_PASSWORD_BODY)

        //  Execute call to sut
        sut.executeLogin(TEST_VALID_USERNAME, TEST_INVALID_PASSWORD)

        verify(mockLoginBodyProvider, times(1)).withCredentials(
            TEST_VALID_USERNAME,
            TEST_INVALID_PASSWORD
        )
        verify(mockLoginBodyProvider, times(1)).createBody()
        verify(mockApi, times(1)).login(TEST_INVALID_PASSWORD_BODY)
        verify(mockAuthorizationManager, never()).setAuthorization(TEST_AUTHORIZATION_STRING)
    }

    @Test
    fun `should not set authorization for invalid credentials (failed request)`() {
        //  Generate invalid request body
        `when`(mockLoginBodyProvider.createBody()).thenReturn(TEST_INVALID_USERNAME_BODY)

        //  Execute call to sut
        sut.executeLogin(TEST_INVALID_USERNAME, TEST_INVALID_PASSWORD)

        verify(mockLoginBodyProvider, times(1)).withCredentials(TEST_INVALID_USERNAME, TEST_INVALID_PASSWORD)
        verify(mockLoginBodyProvider, times(1)).createBody()
        verify(mockApi, times(1)).login(TEST_INVALID_USERNAME_BODY)
        verify(mockAuthorizationManager, never()).setAuthorization(TEST_AUTHORIZATION_STRING)
    }
}