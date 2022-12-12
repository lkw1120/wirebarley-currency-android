package com.lkw1120.currency.ui.viewmodel

import com.lkw1120.currency.usecase.CurrencyUseCase
import com.lkw1120.currency.usecase.model.CountryInfo
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var currencyUseCase: CurrencyUseCase
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        currencyUseCase = Mockito.mock(CurrencyUseCase::class.java)
        viewModel = MainViewModel(currencyUseCase)
    }

    @Test
    fun test_getSenderCountryInfo() {
        val expected = CountryInfo (
            code = "USD",
            country = "미국"
        )
        viewModel.setSenderCountry(expected)
        val actual = viewModel.senderCountryStateFlow.value
        assertEquals(expected, actual)
    }

    @Test
    fun test_getReceiverCountryInfo() {
        val expected = CountryInfo (
            code = "KRW",
            country = "한국"
        )
        viewModel.setReceiverCountry(expected)
        val actual = viewModel.receiverCountryStateFlow.value
        assertEquals(expected, actual)
    }

    @Test
    fun test_setExchangeRateCode() {
        val expected = "KRW/USD"
        val sender = CountryInfo (
            code = "USD",
            country = "미국"
        )
        val receiver = CountryInfo (
            code = "KRW",
            country = "한국"
        )
        viewModel.setSenderCountry(sender)
        viewModel.setReceiverCountry(receiver)
        viewModel.setExchangeRateCode()
        val actual = viewModel.exchangeRateCodeStateFlow.value
        assertEquals(expected, actual)
    }

    @Test
    fun test_setRemittance() {
        val expected = 1000.0
        viewModel.setRemittance(1000.0)
        val actual = viewModel.remittanceStateFlow.value
        assertEquals(0,expected.compareTo(actual))
    }

}