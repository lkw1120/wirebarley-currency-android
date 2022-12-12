package com.lkw1120.currency.usecase

import com.lkw1120.currency.common.ModelMapper
import com.lkw1120.currency.repository.CurrencyRepository
import com.lkw1120.currency.usecase.model.CountryInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


interface CurrencyUseCase {
    suspend fun getCountryInfo(): Flow<List<CountryInfo>>

    suspend fun getExchangeRate(sender: String, receiver: String): Flow<Map<String,Any>>

}

class CurrencyUseCaseImpl @Inject constructor(
    private val currencyRepository: CurrencyRepository
): CurrencyUseCase {

    override suspend fun getCountryInfo(

    ): Flow<List<CountryInfo>> =
        currencyRepository.getCountryInfo()
            .map { it.map { entity -> ModelMapper.mapper(entity) } }

    override suspend fun getExchangeRate(
        sender: String,
        receiver: String
    ): Flow<Map<String, Any>> =
        currencyRepository.getExchangeRate(sender,receiver)
            .map { ModelMapper.convertJsonToMap(it) }

}