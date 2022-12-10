package com.lkw1120.currency.datasource.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("currency_data/live")
    suspend fun getExchangeRate(
        @Query("source") sender: String,
        @Query("currencies") receiver: String
    ): Response<String>
}