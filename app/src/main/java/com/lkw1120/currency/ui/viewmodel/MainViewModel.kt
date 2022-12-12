package com.lkw1120.currency.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.internal.LinkedTreeMap
import com.lkw1120.currency.usecase.CurrencyUseCase
import com.lkw1120.currency.usecase.model.CountryInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val currencyUseCase: CurrencyUseCase
): ViewModel() {

    val senderCountryStateFlow: StateFlow<CountryInfo>
        get() = _senderCountryStateFlow
    private val _senderCountryStateFlow: MutableStateFlow<CountryInfo> =
        MutableStateFlow(CountryInfo( "USD","미국"))

    val receiverCountryStateFlow: StateFlow<CountryInfo>
        get() = _receiverCountryStateFlow
    private val _receiverCountryStateFlow: MutableStateFlow<CountryInfo> =
        MutableStateFlow(CountryInfo("KRW", "한국"))

    val exchangeRateStateFlow: StateFlow<Double>
        get() = _exchangeRateStateFlow
    private val _exchangeRateStateFlow: MutableStateFlow<Double> =
        MutableStateFlow(0.0)

    val exchangeRateCodeStateFlow: StateFlow<String>
        get() = _exchangeRateCodeStateFlow
    private val _exchangeRateCodeStateFlow: MutableStateFlow<String> =
        MutableStateFlow("")

    val timestampStateFlow: StateFlow<Double>
        get() = _timestampStateFlow
    private val _timestampStateFlow: MutableStateFlow<Double> =
        MutableStateFlow(0.0)

    val remittanceStateFlow: StateFlow<Double>
        get() = _remittanceStateFlow
    private val _remittanceStateFlow: MutableStateFlow<Double> =
        MutableStateFlow(0.0)

    val countryInfoStateFlow: StateFlow<List<CountryInfo>>
        get() = _countryInfoStateFlow
    private val _countryInfoStateFlow: MutableStateFlow<List<CountryInfo>> =
        MutableStateFlow(listOf())

    val amountReceivedStateFlow: StateFlow<Double>
        get() = _amountReceivedStateFlow
    private val _amountReceivedStateFlow: MutableStateFlow<Double> =
        MutableStateFlow(0.0)


    fun getCountryInfo() {
        viewModelScope.launch {
            currencyUseCase.getCountryInfo().collect {
                _countryInfoStateFlow.value = it
            }
        }
    }

    fun setSenderCountry(countryInfo: CountryInfo) {
        _senderCountryStateFlow.value = countryInfo
    }

    fun setReceiverCountry(countryInfo: CountryInfo) {
        _receiverCountryStateFlow.value = countryInfo
    }

    fun getExchangeRate() {
        viewModelScope.launch {
            val senderCode = senderCountryStateFlow.value.code
            val receiverCode = receiverCountryStateFlow.value.code
            currencyUseCase.getExchangeRate(
                sender = senderCode,
                receiver = receiverCode
            ).collect {
                it["timestamp"]?.let { timestamp ->
                    _timestampStateFlow.value = timestamp as Double
                }
                it["quotes"]?.let { quotes ->
                    val map: Map<Any, Any> = (quotes as LinkedTreeMap<*, *>).toMap()
                    val exchangeRate = map["$senderCode$receiverCode"]
                    _exchangeRateStateFlow.value = exchangeRate as Double
                }
            }
        }
    }

    fun setExchangeRateCode() {
        val senderCode = senderCountryStateFlow.value.code
        val receiverCode = receiverCountryStateFlow.value.code
        _exchangeRateCodeStateFlow.value = "$receiverCode/$senderCode"
    }

    fun setRemittance(money: Double) {
        _remittanceStateFlow.value = money
    }

    fun calculateRemittance() {
        val exchangeRate = exchangeRateStateFlow.value
        val remittance = remittanceStateFlow.value
        _amountReceivedStateFlow.value = exchangeRate*remittance
    }
}