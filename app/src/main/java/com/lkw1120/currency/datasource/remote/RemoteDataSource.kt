package com.lkw1120.currency.datasource.remote

import com.lkw1120.currency.datasource.remote.api.ApiConnection

object RemoteDataSource {

    private val apiService = ApiConnection.getService()

    fun getApiService() = apiService

}