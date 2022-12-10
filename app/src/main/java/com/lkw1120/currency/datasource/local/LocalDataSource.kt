package com.lkw1120.currency.datasource.local

import android.content.Context
import com.lkw1120.currency.datasource.local.dao.CountryDao

object LocalDataSource {

    private lateinit var database: AppDatabase

    fun setAppDatabase(context: Context) {
        database = AppDatabase.getInstance(context)
    }

    fun getCurrencyDao(): CountryDao =
        database.countryDao()

}
