package com.scc.networking

import com.google.gson.GsonBuilder
import com.scc.networking.configs.RetrofitConfigs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.connectTimeout(RetrofitConfigs.CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            this.readTimeout(RetrofitConfigs.READ_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            this.writeTimeout(RetrofitConfigs.WRITE_TIMEOUT_MS, TimeUnit.MILLISECONDS)
        }.build()

        fun getRetrofitInstance(): Retrofit = Retrofit.Builder()
            .baseUrl(RetrofitConfigs.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .build()
    }
}