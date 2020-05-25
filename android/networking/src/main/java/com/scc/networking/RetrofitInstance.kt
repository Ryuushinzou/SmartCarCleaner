package com.scc.networking

import com.google.gson.GsonBuilder
import com.scc.networking.configs.RetrofitConfigs
import com.scc.networking.interceptors.AuthorizationInterceptor
import com.scc.security.AuthorizationProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class RetrofitInstance {
    companion object {
        private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val additionalInterceptors: ArrayList<Interceptor> = ArrayList()

        private val clientBuilder = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.connectTimeout(RetrofitConfigs.CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            this.readTimeout(RetrofitConfigs.READ_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            this.writeTimeout(RetrofitConfigs.WRITE_TIMEOUT_MS, TimeUnit.MILLISECONDS)
        }

        fun getRetrofitInstance(): Retrofit = Retrofit.Builder()
            .baseUrl(RetrofitConfigs.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(
                clientBuilder.also { builder ->
                    additionalInterceptors.forEach { interceptor ->
                        builder.addInterceptor(
                            interceptor
                        )
                    }
                }.build()
            )
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .build()

        fun setAuthorizationProvider(authorizationProvider: AuthorizationProvider) {
            addInterceptor(AuthorizationInterceptor(authorizationProvider))
        }

        private fun addInterceptor(interceptor: Interceptor) {
            additionalInterceptors.add(interceptor)
        }
    }
}