package com.lkw1120.currency.datasource.remote.api

import com.lkw1120.currency.common.API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiConnection {

    private const val baseUrl: String = "https://api.apilayer.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val apiInterceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("apikey",API_KEY)
            .build()
        chain.proceed(newRequest)
    }

    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(apiInterceptor)
        addInterceptor(loggingInterceptor)
    }.build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    fun getService(): ApiService =
        retrofit.create(ApiService::class.java)
}