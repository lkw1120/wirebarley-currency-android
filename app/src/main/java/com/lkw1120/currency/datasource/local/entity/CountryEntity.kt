package com.lkw1120.currency.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_info")
data class CountryEntity (
    @PrimaryKey
    val code: String,
    val country: String
)