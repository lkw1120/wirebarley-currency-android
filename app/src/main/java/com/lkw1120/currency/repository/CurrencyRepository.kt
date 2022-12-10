package com.lkw1120.currency.repository

import com.lkw1120.currency.datasource.local.LocalDataSource
import com.lkw1120.currency.datasource.local.entity.CountryEntity
import com.lkw1120.currency.datasource.remote.RemoteDataSource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface CurrencyRepository {
    suspend fun getCountryInfo(): Flow<List<CountryEntity>>

    suspend fun getExchangeRate(sender: String, receiver: String): Flow<String>
}

class CurrencyRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): CurrencyRepository {

    override suspend fun getCountryInfo(

    ): Flow<List<CountryEntity>> =
        localDataSource.getCurrencyDao().getCountryInfo()

    override suspend fun getExchangeRate(
        sender: String,
        receiver: String
    ): Flow<String> = flow {

        remoteDataSource.getApiService().getExchangeRate(sender, receiver).let {
            if(it.isSuccessful) {
                emit(it.body()!!)
            }
        }


        /**
         * API 횟수제한으로 인한 테스트용 데이터
         *
        val json = "{\n" +
                "    \"success\": true,\n" +
                "    \"timestamp\": 1670566142,\n" +
                "    \"source\": \"USD\",\n" +
                "    \"quotes\": {\n" +
                "        \"USDJPY\": 136.140981,\n" +
                "        \"USDKRW\": 1300.650157,\n" +
                "        \"USDPHP\": 55.328499\n" +
                "    }\n" +
                "}"
        emit(json)
         */
    }
}