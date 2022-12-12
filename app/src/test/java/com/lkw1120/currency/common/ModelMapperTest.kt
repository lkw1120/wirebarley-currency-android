package com.lkw1120.currency.common

import com.lkw1120.currency.datasource.local.entity.CountryEntity
import com.lkw1120.currency.usecase.model.CountryInfo
import org.junit.Assert.*
import org.junit.Test

class ModelMapperTest {

    @Test
    fun test_mapper() {
        val countryEntity = CountryEntity(
            code = "USD",
            country = "미국"
        )
        val expected = CountryInfo(
            code = "USD",
            country = "미국"
        )
        val actual: CountryInfo = ModelMapper.mapper(countryEntity)
        assertEquals(expected,actual)
    }

    @Test
    fun test_convertJsonToMap() {
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
        val expected: Map<String, Any> = mapOf(
            "success" to true,
            "timestamp" to 1670566142.toDouble(),
            "source" to "USD",
            "quotes" to mapOf(
                "USDJPY" to 136.140981,
                "USDKRW" to 1300.650157,
                "USDPHP" to 55.328499
            )
        )
        val actual = ModelMapper.convertJsonToMap(json)
        assertEquals(expected,actual)
    }
}