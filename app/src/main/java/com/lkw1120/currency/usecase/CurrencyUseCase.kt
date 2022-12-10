package com.lkw1120.currency.usecase

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.lkw1120.currency.common.ModelMapper
import com.lkw1120.currency.repository.CurrencyRepository
import com.lkw1120.currency.usecase.model.CountryInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type
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
            .map { response ->
                val type: Type = object : TypeToken<Map<String?, Any?>?>() {}.type
                val map: Map<String,Any> =
                    (Gson().fromJson(response, type) as LinkedTreeMap<String, Any>).toMap()
                map
            }


}