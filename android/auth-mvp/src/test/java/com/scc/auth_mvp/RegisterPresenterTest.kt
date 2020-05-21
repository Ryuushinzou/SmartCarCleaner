package com.scc.auth_mvp

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.scc.auth_api.AuthApi
import com.scc.auth_api.providers.LoginBodyProvider
import com.scc.auth_api.providers.RegisterBodyProvider
import com.scc.auth_mvp.Register.TEST_INVALID_REGISTER_BODY
import com.scc.auth_mvp.Register.TEST_VALID_REGISTER_BODY
import com.scc.auth_mvp.login.LoginContract
import com.scc.auth_mvp.login.LoginPresenter
import com.scc.auth_mvp.register.RegisterContract
import com.scc.auth_mvp.register.RegisterPresenter
import com.scc.security.AuthorizationProvider
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class RegisterPresenterTest {

    private val testSubscriptions = CompositeDisposable()
    private val mockApi: AuthApi = mock {
        on { register(eq(TEST_VALID_REGISTER_BODY)) } doReturn Observable.just(
            TEST_REGISTER_SUCCESS_RESPONSE
        )
        on { register(eq(TEST_INVALID_REGISTER_BODY)) } doReturn Observable.error(
            TEST_INVALID_CREDENTIALS_EXCEPTION
        )
    }
    private val mockRegisterBodyProvider: RegisterBodyProvider = mock {

    }
    private val testScheduler: Scheduler = Schedulers.trampoline()

    private val mockView: RegisterContract.View = mock { }

    private val sut = RegisterPresenter(
        testSubscriptions,
        mockApi,
        mockRegisterBodyProvider,
        testScheduler,
        testScheduler
    )

    @Before
    fun setup() {
        `when`(
            mockRegisterBodyProvider.with(
                com.nhaarman.mockitokotlin2.any(),
                com.nhaarman.mockitokotlin2.any(),
                com.nhaarman.mockitokotlin2.any(),
                com.nhaarman.mockitokotlin2.any()
            )
        ).thenReturn(mockRegisterBodyProvider)
    }

    @Test
    fun `should set authorization for valid provided credentials (successful request)`() {
        //  Generate valid request body
        `when`(mockRegisterBodyProvider.createBody()).thenReturn(TEST_VALID_REGISTER_BODY)

        sut.subscribe()
        sut.attach(mockView)

        //  Execute call to sut
        sut.executeRegister(
            TEST_VALID_USERNAME,
            TEST_VALID_PASSWORD,
            TEST_VALID_PHONE,
            TEST_VALID_PASSWORD
        )

        //  Loading state should be requested
        verify(mockView).showLoading()

        verify(mockRegisterBodyProvider, times(1)).with(
            TEST_VALID_USERNAME,
            TEST_INVALID_EMAIL,
            TEST_VALID_PHONE,
            TEST_VALID_PASSWORD
        )
        verify(mockRegisterBodyProvider, times(1)).createBody()
        verify(mockApi, times(1)).register(TEST_VALID_REGISTER_BODY)

        //  Successful state should be requested
        verify(mockView).onRegisterSuccessful()
    }

    @Test
    fun `should not set authorization for invalid password (failed request)`() {
        //  Generate invalid request body
        `when`(mockRegisterBodyProvider.createBody()).thenReturn(TEST_INVALID_REGISTER_BODY)

        sut.subscribe()
        sut.attach(mockView)

        //  Execute call to sut
        sut.executeRegister(
            TEST_INVALID_USERNAME,
            TEST_INVALID_EMAIL,
            TEST_INVALID_PHONE,
            TEST_INVALID_PASSWORD
        )

        //  Loading state should be requested
        verify(mockView).showLoading()

        verify(mockRegisterBodyProvider, times(1)).with(
            TEST_INVALID_USERNAME,
            TEST_INVALID_EMAIL,
            TEST_INVALID_PHONE,
            TEST_INVALID_PASSWORD
        )
        verify(mockRegisterBodyProvider, times(1)).createBody()
        verify(mockApi, times(1)).register(TEST_INVALID_REGISTER_BODY)

        //  Error state should be requested
        verify(mockView).showError(eq(TEST_INVALID_CREDENTIALS_EXCEPTION))
    }
}