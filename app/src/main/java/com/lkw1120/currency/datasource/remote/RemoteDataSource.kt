package com.lkw1120.currency.datasource.remote

import com.lkw1120.currency.datasource.remote.api.ApiConnection
import com.lkw1120.currency.datasource.remote.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface RemoteDataSource {
    fun getExchangeRate(sender: String, receiver: String): Flow<String>

}
class RemoteDataSourceImpl @Inject constructor(

): RemoteDataSource {

    private val apiService: ApiService by lazy {
        ApiConnection.getService()
    }

    override fun getExchangeRate(sender: String, receiver: String): Flow<String> = flow {
        apiService.getExchangeRate(sender, receiver).let {
            if (it.isSuccessful) {
                emit(it.body()!!)
            }
        }
/*
        /**
         * API 횟수제한으로 인한 테스트용 데이터
         */

        val json = """{
        "success": true,
        "timestamp": 1670566142,
        "source": "USD",
        "quotes": {
            "USDJPY": 136.140981,
            "USDKRW": 1300.650157,
            "USDPHP": 55.328499
            }
        }"""
        emit(json)
*/
    }

}