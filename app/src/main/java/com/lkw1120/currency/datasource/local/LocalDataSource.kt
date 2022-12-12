package com.lkw1120.currency.datasource.local

import android.content.Context
import com.lkw1120.currency.datasource.local.entity.CountryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDataSource {
    fun getCountryInfo(): Flow<List<CountryEntity>>

}
class LocalDataSourceImpl @Inject constructor (
    private val context: Context
): LocalDataSource {

    private val database: AppDatabase by lazy {
        AppDatabase.getInstance(context)
    }

    override fun getCountryInfo() =
        database.countryDao().getCountryInfo()

}
