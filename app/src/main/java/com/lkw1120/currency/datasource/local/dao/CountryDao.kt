package com.lkw1120.currency.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lkw1120.currency.datasource.local.entity.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CountryEntity>)

    @Query("SELECT * FROM country_info")
    fun getCountryInfo(): Flow<List<CountryEntity>>
}