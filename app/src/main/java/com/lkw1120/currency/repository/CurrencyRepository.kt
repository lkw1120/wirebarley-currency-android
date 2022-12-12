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
        localDataSource.getCountryInfo()

    override suspend fun getExchangeRate(
        sender: String,
        receiver: String
    ): Flow<String> =
        remoteDataSource.getExchangeRate(sender, receiver)
}